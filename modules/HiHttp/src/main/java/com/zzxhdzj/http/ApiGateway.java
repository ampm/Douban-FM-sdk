package com.zzxhdzj.http;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiGateway {

    private final Http http = new Http();

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public <T extends ApiResponse> void makeRequest(ApiRequest<T> apiRequest, final ApiResponseCallbacks<T> responseCallbacks) {
        responseCallbacks.onStart();
        new RemoteCallTask(responseCallbacks).execute(apiRequest);
    }

    protected void dispatch(ApiResponse apiResponse, ApiResponseCallbacks responseCallbacks) {
        if (apiResponse.isSuccess()) {
            try {
                responseCallbacks.onSuccess(apiResponse);
            } catch (Exception e) {
                Log.e(ApiGateway.class.getName(), "Error processing response", e);
                responseCallbacks.onProcessFailure(apiResponse);
            }
        } else {
            responseCallbacks.onRequestFailure(apiResponse);
        }
        responseCallbacks.onComplete();
    }

    private void closeStream(InputStream responseBody) {
        if (responseBody != null) {
            try {
                responseBody.close();
            } catch (IOException ignored) {
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private class RemoteCallTask extends AsyncTask<ApiRequest, Void, ApiResponse> {
        private final ApiResponseCallbacks responseCallbacks;

        public RemoteCallTask(ApiResponseCallbacks responseCallbacks) {
            this.responseCallbacks = responseCallbacks;
        }

        @Override
        protected ApiResponse doInBackground(ApiRequest... apiRequests) {
            ApiRequest apiRequest = apiRequests[0];
            InputStream responseBody = null;
            Http.Response response = null;
            try {
                try {
                    Log.d("ApiGateway", "req info [url=" + apiRequest.getUrlString() + " ]");
                    if (HttpPost.METHOD_NAME.equals(apiRequest.getMethod())) {
                        response = http.post(apiRequest.getUrlString(), apiRequest.getHeaders(), apiRequest.getPostEntity(), apiRequest.allowRedirect);
                    } else if (HttpGet.METHOD_NAME.equals(apiRequest.getMethod())) {
                        response = http.get(apiRequest.getUrlString(), apiRequest.getHeaders(), apiRequest.allowRedirect);
                    } else {
                        throw new RuntimeException("Unsupported Http Method!");
                    }
                } catch (Exception e) {
                    Log.d("ApiGateway", "request failed\n");
                    return apiRequest.createResponse(WrappedHttpError.REQUEST_ERROR.getCode(), (response == null) ? null : response.getHeaderFields());
                }

                try {
                    responseBody = response.getResponseBody();
                    ApiResponse apiResponse = apiRequest.createResponse(response.getStatusCode(), response.getHeaderFields());
                    Log.d("ApiGateway", "resp info [response code = " + apiResponse.getHttpResponseCode() + "]\n");
                    apiResponse.consumeResponse(responseBody);
                    return apiResponse;
                } catch (Exception e) {
                    Log.d("ApiGateway", "resp process failed\n");
                    return apiRequest.createResponse(WrappedHttpError.CONSUME_ERROR.getCode(), (response == null) ? null : response.getHeaderFields());
                }
            }finally {
                closeStream(responseBody);
            }

        }

        @Override
        protected void onPostExecute(ApiResponse apiResponse) {
            dispatch(apiResponse, responseCallbacks);
        }
    }
}
