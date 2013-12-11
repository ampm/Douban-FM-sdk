package com.zzxhdzj.douban.api.channels.action;

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
 * Date: 12/10/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelActionGatewayTest extends BaseGatewayTestCase {

    private int channelId;
    private ChannelActionGateway channelActionGateway;

    @Before
    public void setUp() {
        super.setUp();
        channelId = 1;
        channelActionGateway = new ChannelActionGateway(douban, apiGateway);

    }

    @Test
    public void shouldReturnFavSuccess() throws Exception {
        channelActionGateway.favAChannel(ChannelActionType.FAV_CHANNEL, channelId, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_CHANNEL_JSON, null);
        assertNull(channelActionGateway.failureResponse);
        assertTrue(channelActionGateway.onCompleteWasCalled);
    }

    @Test
    public void shouldReturnUnFavSuccess() throws Exception {
        channelActionGateway.favAChannel(ChannelActionType.UNFAV_CHANNEL, channelId, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_CHANNEL_JSON, null);
        assertNull(channelActionGateway.failureResponse);
        assertTrue(channelActionGateway.onCompleteWasCalled);
    }

    @Test
    public void shouldHaveCookie() {
        channelActionGateway.favAChannel(ChannelActionType.FAV_CHANNEL, channelId, new Callback());
        ApiRequest apiRequest = apiGateway.getLatestRequest();
        assertTrue(apiRequest.getHeaders().containsKey("Cookie"));
        assertThat(apiRequest.getHeaders().get("Cookie").toString(), equalTo(""));
    }

    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        channelActionGateway.favAChannel(ChannelActionType.FAV_CHANNEL, channelId, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        assertNotNull(channelActionGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(), equalTo("500"));

    }

    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        channelActionGateway.favAChannel(ChannelActionType.FAV_CHANNEL, channelId, badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_CHANNEL_JSON, null);
        assertNotNull(channelActionGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(), equalTo("-1"));

    }

}
