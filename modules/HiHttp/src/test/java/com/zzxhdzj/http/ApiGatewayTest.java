package com.zzxhdzj.http;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import com.zzxhdzj.http.mock.TestApiResponseCallbacks;
import com.zzxhdzj.http.mock.TestResponses;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class ApiGatewayTest {
    private ApiGateway apiGateway;
    private TestApiResponseCallbacks responseCallbacks;//mock
    private Callback callbackFailed = new Callback() {
        @Override
        public void onSuccess() {
            throw new RuntimeException("Boom!");
        }
    };
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        apiGateway = new ApiGateway();
        responseCallbacks = new TestApiResponseCallbacks();
        server = new MockWebServer();
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }
    @Test
    public void dispatch_shouldCallOnSuccessWhenApiResponseIsSuccess() throws Exception {
        JsonApiResponse jsonApiResponse = new JsonApiResponse(200);
        apiGateway.dispatch(jsonApiResponse, responseCallbacks);
        assertThat(responseCallbacks.successResponse, sameInstance(jsonApiResponse));
        assertThat(responseCallbacks.failureResponse, nullValue());
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void dispatch_shouldCallOnFailureWhenApiResponseIsFailure() throws Exception {
        JsonApiResponse jsonApiResponse = new JsonApiResponse(500);
        apiGateway.dispatch(jsonApiResponse, responseCallbacks);
        assertThat(responseCallbacks.failureResponse, sameInstance((ApiResponse) jsonApiResponse));
        assertThat(responseCallbacks.successResponse, nullValue());
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }
    @Test
    public void dispatch_shouldTriggerRequestFailureWhenGetTimeout() throws Exception {
        server.play();
        apiGateway.makeRequest(new JsonIntentTestGetRequest(), responseCallbacks);
        assertThat(responseCallbacks.failureResponse, not(nullValue()));
        assertThat(responseCallbacks.failureResponse.getHttpResponseCode(), equalTo(WrappedHttpError.REQUEST_ERROR.getCode()));
        assertThat(responseCallbacks.successResponse, nullValue());
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }
    @Test
    public void dispatch_shouldTriggerRequestFailureWhenPostTimeout() throws Exception {
        server.play();
        apiGateway.makeRequest(new JsonIntentTestPostRequest(), responseCallbacks);
        assertThat(responseCallbacks.failureResponse, not(nullValue()));
        assertThat(responseCallbacks.failureResponse.getHttpResponseCode(), equalTo(WrappedHttpError.REQUEST_ERROR.getCode()));
        assertThat(responseCallbacks.successResponse, nullValue());
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void dispatch_shouldTriggerAJsonParseErrorWhenHttpRespOneInvalidJsonContent() throws Exception {
        server.enqueue(new MockResponse().setBody("i am not a json string"));
        server.play();
        apiGateway.makeRequest(new JsonIntentTestPostRequest(), responseCallbacks);
        assertThat(responseCallbacks.failureResponse, not(nullValue()));
        assertThat(responseCallbacks.failureResponse.getHttpResponseCode(), equalTo(WrappedHttpError.CONSUME_ERROR.getCode()));
        assertThat(responseCallbacks.successResponse, nullValue());
        assertThat(responseCallbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void dispatch_shouldCallOnFailureWhenProcessRespFails() {
        JsonApiResponse jsonApiResponse = new JsonApiResponse(200);
        TestApiResponseCallbacks callbacks = new TestApiResponseCallbacks() {
            @Override
            public void onSuccess(JsonApiResponse successResponse) throws IOException {
                throw new RuntimeException("Boom!");
            }
        };
        apiGateway.dispatch(jsonApiResponse, callbacks);
        assertThat(callbacks.onProcessFailureWasCalled, equalTo(true));
        assertThat(callbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void dispatch_shouldCallOnFailureWhenCallbackFails() {
        JsonApiResponse jsonApiResponse = new JsonApiResponse(200);
        TestApiResponseCallbacks callbacks = new TestApiResponseCallbacks() {
            @Override
            public void onSuccess(JsonApiResponse successResponse) throws IOException {
                try {
                    callbackFailed.onSuccess();
                } catch (Exception e) {
                    onCallbackFailure(successResponse);
                }

            }
        };
        apiGateway.dispatch(jsonApiResponse, callbacks);
        assertThat(callbacks.onCallBackFailureWasCalled, equalTo(true));
        assertThat(callbacks.onCompleteWasCalled, equalTo(true));
    }

    @Test
    public void remoteCallTask_shouldMakeRemotePostCalls() throws InterruptedException, IOException {
        server.enqueue(new MockResponse().setBody(TestResponses.GENERIC_JSON));
        server.play();
        JsonIntentTestPostRequest apiRequest = new JsonIntentTestPostRequest();
        apiGateway.makeRequest(apiRequest, responseCallbacks);
        RecordedRequest request  = server.takeRequest();
        assertThat(request.getMethod(), equalTo(HttpPost.METHOD_NAME));
        assertThat(request.getUtf8Body(), equalTo("a post body"));
        assertThat(request.getHeader("foo"), equalTo("bar"));
        assertThat(responseCallbacks.successResponse, notNullValue());
    }
    @Test
    public void remoteCallTask_shouldMakeRemoteGetCalls() throws InterruptedException, IOException {
        server.enqueue(new MockResponse().setBody(TestResponses.GENERIC_JSON));
        server.play();
        JsonIntentTestGetRequest apiRequest = new JsonIntentTestGetRequest();
        apiGateway.makeRequest(apiRequest, responseCallbacks);
        RecordedRequest request  = server.takeRequest();
        assertThat(request.getMethod(), equalTo(HttpGet.METHOD_NAME));
        assertThat(request.getHeader("foo"), equalTo("bar"));
        assertThat(responseCallbacks.successResponse, notNullValue());
    }

    class JsonIntentTestPostRequest extends ApiRequest<JsonApiResponse> {

        JsonIntentTestPostRequest() {
            super.setBaseUrl(server.getUrl("/").toString());
        }



        @Override
        public HttpEntity getPostEntity() throws Exception {
            StringEntity stringEntity = new StringEntity("a post body");
            return stringEntity;
        }

        @Override
        public JsonApiResponse createResponse(int statusCode, Map<String, List<String>> headers) {
            return new JsonApiResponse(statusCode,headers);
        }

        @Override
        public String getMethod() {
            return HttpPost.METHOD_NAME;
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> headers = super.getHeaders();
            headers.put("foo", "bar");
            return headers;
        }

    }
    class JsonIntentTestGetRequest extends ApiRequest<JsonApiResponse> {

        JsonIntentTestGetRequest() {
            super.setBaseUrl(server.getUrl("/").toString());
        }

        @Override
        public JsonApiResponse createResponse(int statusCode, Map<String, List<String>> headers) {
            return new JsonApiResponse(statusCode,headers);
        }

        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> headers = super.getHeaders();
            headers.put("foo", "bar");
            return headers;
        }

    }


}
