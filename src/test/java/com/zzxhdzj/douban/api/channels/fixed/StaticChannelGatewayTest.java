package com.zzxhdzj.douban.api.channels.fixed;

import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.mock.TestApiGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
@RunWith(RobolectricTestRunner.class)
public class StaticChannelGatewayTest {
    public TestApiGateway apiGateway;
    StaticChannelGateway staticChannelGateway;
    private Douban douban;
    private int start;
    private int limit;

    @Before
    public void setUp() {
        apiGateway = new TestApiGateway();
        douban = new Douban();
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


}
