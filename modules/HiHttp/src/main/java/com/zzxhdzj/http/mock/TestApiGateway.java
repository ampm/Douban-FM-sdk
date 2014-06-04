package com.zzxhdzj.http.mock;

import com.zzxhdzj.http.*;
import com.zzxhdzj.http.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zzxhdzj.http.util.Strings.asStream;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestApiGateway extends ApiGateway {
    List<Pair<ApiRequest, ApiResponseCallbacks>> pendingRequests = new ArrayList<Pair<ApiRequest, ApiResponseCallbacks>>();

    @Override
    public void makeRequest(ApiRequest apiRequest, ApiResponseCallbacks responseCallbacks) {
        pendingRequests.add(Pair.of(apiRequest, responseCallbacks));
    }

    public void simulateTextResponse(int httpCode, String responseBody,Map<String, List<String>> headers) throws Exception {
        ensurePendingRequests();
        TextApiResponse apiResponse = new TextApiResponse(httpCode,headers);
        apiResponse.consumeResponse(asStream(responseBody));
        dispatch(apiResponse, unshiftEarliestRequest().mRespCallback);
    }
    public void simulateJsonResponse(int httpCode, String responseBody, Map<String, List<String>> headers) throws Exception {
        ensurePendingRequests();
        JsonApiResponse apiResponse = new JsonApiResponse(httpCode,headers);
        apiResponse.consumeResponse(asStream(responseBody));
        dispatch(apiResponse, unshiftEarliestRequest().mRespCallback);
    }
    public void simulateXmlResponse(int httpCode, String responseBody,Map<String, List<String>> headers) throws Exception {
        ensurePendingRequests();
        XmlApiResponse apiResponse = new XmlApiResponse(httpCode,headers);
        apiResponse.consumeResponse(asStream(responseBody));
        dispatch(apiResponse, unshiftEarliestRequest().mRespCallback);
    }
    public ApiRequest getLatestRequest() {
        ensurePendingRequests();
        return pendingRequests.get(pendingRequests.size() - 1).mReq;
    }

    private void ensurePendingRequests() {
        if (pendingRequests.isEmpty()) {
            throw new RuntimeException("No pending requests to simulate response for");
        }
    }

    private Pair<ApiRequest, ApiResponseCallbacks> unshiftEarliestRequest() {
        return pendingRequests.remove(0);
    }
}
