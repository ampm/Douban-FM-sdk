package com.zzxhdzj.douban.api.channels.favorited;

import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.channels.action.ChannelActionType;
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
public class FavoritedChannelGatewayTest extends BaseGatewayTestCase {
    FavoritedChannelGateway favoritedChannelGateway;

    @Before
    public void setUp() {
        super.setUp();
        favoritedChannelGateway = new FavoritedChannelGateway(douban, apiGateway);
    }

    @Test
    public void shouldFetchOneHotChannelWithCorrectResp() throws Exception {
        favoritedChannelGateway.fetchFavChannels(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.HOT_CHANNELS_JSON, null);
        assertNull(favoritedChannelGateway.failureResponse);
        assertTrue(favoritedChannelGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
    }

    @Test
    public void shouldHaveCookie() throws Exception {
        favoritedChannelGateway.fetchFavChannels(new Callback());
        ApiRequest apiRequest = apiGateway.getLatestRequest();
        assertTrue(apiRequest.getHeaders().containsKey("Cookie"));
        assertThat(apiRequest.getHeaders().get("Cookie").toString(), equalTo(""));
    }

    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        favoritedChannelGateway.fetchFavChannels(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        assertNotNull(favoritedChannelGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(), equalTo("500"));
    }

    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        favoritedChannelGateway.fetchFavChannels(badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.HOT_CHANNELS_JSON, null);
        assertNotNull(favoritedChannelGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(), equalTo("-1"));

    }
}
