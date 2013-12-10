package com.zzxhdzj.douban.api.songs.action;

import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnFavSongGatewayTest extends BaseGatewayTestCase {
    private int sid, channelId;
    private UnFavSongGateway unFavSongGateway;

    @Before
    public void setUp() {
        super.setUp();
        sid = 551805;
        channelId = 0;
        unFavSongGateway = new UnFavSongGateway(douban,apiGateway);
    }

    @Test
    public void shouldFavASongSuccess() throws Exception {
        unFavSongGateway.unfavASong(channelId, sid, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.UNFAV_A_SONG_JSON, null);
        assertTrue(unFavSongGateway.onCompleteWasCalled);
        assertNull(unFavSongGateway.failureResponse);
        assertThat(douban.songs.size(), equalTo(2));
    }

    @Test
    public void shouldFavASongFail() throws Exception {
        unFavSongGateway.unfavASong(channelId, sid, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.UNFAV_A_SONG_FAIL_JSON, null);
        assertTrue(unFavSongGateway.onCompleteWasCalled);
        assertNotNull(unFavSongGateway.failureResponse);
        assertNull(douban.songs);
    }
}
