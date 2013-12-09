package com.zzxhdzj.douban.api.channels.recomment;

import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.mock.TestApiGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class RecommendChannelsGatewayTest {

    private TestApiGateway apiGateway;
    private ArrayList<Integer> channelIds;
    private RecommendChannelsGateway recommendChannelsGateway;
    private Douban douban;

    @Before
    public void setUp() {
        apiGateway = new TestApiGateway();
        channelIds = new ArrayList<Integer>();
        channelIds.add(2);
        channelIds.add(61);
        channelIds.add(9);
        channelIds.add(14);
        douban = new Douban();
        recommendChannelsGateway = new RecommendChannelsGateway(douban, apiGateway);
    }

    @Test
    public void shouldFetchOneChannelBySpecificGenre() throws Exception {
        recommendChannelsGateway.query(channelIds, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.REC_CHANNELS_JSON, null);
        assertNull(recommendChannelsGateway.failureResponse);
        assertTrue(recommendChannelsGateway.onCompleteWasCalled);
        assertNotNull(douban.recommendChannle);
        assertThat(douban.recommendChannle.name, equalTo("JUST FEELING"));
    }
}
