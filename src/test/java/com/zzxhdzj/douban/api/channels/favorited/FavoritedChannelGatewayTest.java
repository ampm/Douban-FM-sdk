package com.zzxhdzj.douban.api.channels.favorited;

import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
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
        favoritedChannelGateway.fetchHotChannels(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.HOT_CHANNELS_JSON, null);
        assertNull(favoritedChannelGateway.failureResponse);
        assertTrue(favoritedChannelGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
    }

    @Test
    public void shouldFetchOneTrendingChannelWithCorrectResp() throws Exception {
        favoritedChannelGateway.fetchTrendingChannels(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAST_CHANNELS_JSON, null);
        assertNull(favoritedChannelGateway.failureResponse);
        assertTrue(favoritedChannelGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
    }


}
