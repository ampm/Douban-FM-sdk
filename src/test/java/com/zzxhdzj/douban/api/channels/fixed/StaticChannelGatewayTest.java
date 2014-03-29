package com.zzxhdzj.douban.api.channels.fixed;

import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.Callback;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaticChannelGatewayTest extends BaseGatewayTestCase {
    StaticChannelGateway staticChannelGateway;
    private int start;
    private int limit;

    @Before
    public void setUp() {
        super.setUp();
        start = 1;
        limit = 1;
        staticChannelGateway = new StaticChannelGateway(douban, apiGateway);
    }

    @Test
    public void shouldFetchOneHotChannelWithCorrectResp() throws Exception {
        staticChannelGateway.fetchHotChannels(start, limit, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.HOT_CHANNELS_JSON, null);
        assertNull(staticChannelGateway.failureResponse);
        assertTrue(staticChannelGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
    }

    @Test
    public void shouldFetchOneTrendingChannelWithCorrectResp() throws Exception {
        staticChannelGateway.fetchTrendingChannels(start, limit, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAST_CHANNELS_JSON, null);
        assertNull(staticChannelGateway.failureResponse);
        assertTrue(staticChannelGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
    }

    @Test
    public void shouldHaveCookie() throws Exception {
        staticChannelGateway.fetchTrendingChannels(start, limit, new Callback());
        ApiRequest apiRequest = apiGateway.getLatestRequest();
        assertTrue(apiRequest.getHeaders().containsKey("Cookie"));
        assertThat(apiRequest.getHeaders().get("Cookie").toString(), equalTo(""));
    }

    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        staticChannelGateway.fetchTrendingChannels(start, limit, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        assertNotNull(staticChannelGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.INTERNAL_ERROR.getCode()));
    }

    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        staticChannelGateway.fetchTrendingChannels(start, limit, badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.FAST_CHANNELS_JSON, null);
        assertNotNull(staticChannelGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.CALLER_ERROR.getCode()));

    }
}
