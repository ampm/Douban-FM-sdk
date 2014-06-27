package com.zzxhdzj.http;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import com.zzxhdzj.http.asynctask.PoolAsyncTask;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

import java.io.IOException;
import java.io.InputStream;



public class ApiGateway<T extends ApiResponse> {
	private final Http http = new Http();

	public T makeSyncRequest(ApiRequest<T> apiRequest) {
		InputStream responseBody = null;
			Http.Response response;
			try {
				if (HttpPost.METHOD_NAME.equals(apiRequest.getMethod())) {
					Log.d("ApiGateway", "req info [url=" + apiRequest.getUrlString() + " ,headers=" + apiRequest.getHeaders() + " ]");
					response = http.post(apiRequest.getUrlString(), apiRequest.getHeaders(), apiRequest.getPostEntity(),apiRequest.allowRedirect);
				} else if (HttpGet.METHOD_NAME.equals(apiRequest.getMethod())) {
					response = http.get(apiRequest.getUrlString(), apiRequest.getHeaders(),apiRequest.allowRedirect);
				} else if (HttpPut.METHOD_NAME.equals(apiRequest.getMethod())) {
					response = http.post(apiRequest.getUrlString(), apiRequest.getHeaders(), apiRequest.getPostEntity(),apiRequest.allowRedirect);
				} else {
					throw new RuntimeException("Unsupported Http Method!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return apiRequest.createResponse(WrappedHttpError.REQUEST_ERROR.getCode(), null);
			}


			responseBody = response.getResponseBody();
			T apiResponse = apiRequest.createResponse(response.getStatusCode(),response.getHeaderFields());
			Log.d("ApiGateway", "resp info [response code = " + apiResponse.getHttpResponseCode() + "]\n");
			try {
				apiResponse.consumeResponse(responseBody);
				return apiResponse;
			} catch (Exception e) {
				Log.d("ApiGateway", "resp process failed\n");
				return apiRequest.createResponse(WrappedHttpError.CONSUME_ERROR.getCode(), (response == null) ? null : response.getHeaderFields());
			}finally {
				if (responseBody != null) {
					try {
						responseBody.close();
					} catch (IOException ignored) {
					}
				}
			}

	}
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public void makeRequest(ApiRequest<T> apiRequest, final ApiResponseCallbacks<T> responseCallbacks) {
		responseCallbacks.onStart();
//		new RemoteCallTask(responseCallbacks).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,apiRequest);
				new RemoteCallTask(responseCallbacks).execute(apiRequest);
	}


	protected void dispatch(T apiResponse, ApiResponseCallbacks<T> responseCallbacks) {
		if (apiResponse.isSuccess()) {
			try {
				responseCallbacks.onSuccess(apiResponse);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(e.getClass().getName(), "ApiGateway:dispatch:Error:\n" + e.getMessage());
				responseCallbacks.onProcessFailure(apiResponse);
			}
		} else {
			Log.e("request failed", "ApiGateway:response:\n" + apiResponse.getHttpResponseCode());
			responseCallbacks.onRequestFailure(apiResponse);
		}
		responseCallbacks.onComplete();
	}
	private class RemoteCallTask extends PoolAsyncTask<ApiRequest<T>, Void, T> {
//	private class RemoteCallTask extends AsyncTask<ApiRequest<T>, Void, T> {
		private final ApiResponseCallbacks<T> responseCallbacks;

		public RemoteCallTask(ApiResponseCallbacks<T> responseCallbacks) {
			this.responseCallbacks = responseCallbacks;
		}

		@Override
		protected T doInBackground(ApiRequest<T>... apiRequests) {
			ApiRequest<T> apiRequest = apiRequests[0];
			return makeSyncRequest(apiRequest);
		}

		@Override
		protected void onPostExecute(T apiResponse) {
			dispatch(apiResponse, responseCallbacks);
		}
	}
}
