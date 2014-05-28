package com.zzxhdzj.douban.api.songs.action;

import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class SongActionGatewayTest extends BaseGatewayTestCase {
    private int sid, channelId;
    private SongActionGateway favSongGateway;

    @Before
    public void setUp() {
        super.setUp();
        sid = 551805;
        channelId = 0;
        favSongGateway = new SongActionGateway(douban, apiGateway);
    }

    @Test
    public void shouldFavASongSuccess() throws Exception {
        favSongGateway.songAction(SongActionType.FAV, channelId, sid, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_SONG_JSON, null);
        assertTrue(favSongGateway.onCompleteWasCalled);
        assertNull(favSongGateway.failureResponse);
        assertThat(douban.songs.size(), equalTo(2));
    }

    @Test
    public void shouldFavASongFail() throws Exception {
        favSongGateway.songAction(SongActionType.FAV, channelId, sid, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_SONG_FAIL_JSON, null);
        assertTrue(favSongGateway.onCompleteWasCalled);
        assertNotNull(favSongGateway.failureResponse);
        assertNull(douban.songs);
    }

    @Test
    public void shouldUnFavASongSuccess() throws Exception {
        favSongGateway.songAction(SongActionType.UNFAV, channelId, sid, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_SONG_JSON, null);
        assertTrue(favSongGateway.onCompleteWasCalled);
        assertNull(favSongGateway.failureResponse);
        assertThat(douban.songs.size(), equalTo(2));
    }

    @Test
    public void shouldUnFavASongFail() throws Exception {
        favSongGateway.songAction(SongActionType.UNFAV, channelId, sid, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_SONG_FAIL_JSON, null);
        assertTrue(favSongGateway.onCompleteWasCalled);
        assertNotNull(favSongGateway.failureResponse);
        assertNull(douban.songs);
    }


    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        favSongGateway.songAction(SongActionType.UNFAV, channelId, sid, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        assertNotNull(favSongGateway.failureResponse);
        Assert.assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.INTERNAL_ERROR.getCode()));

    }

    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        favSongGateway.songAction(SongActionType.UNFAV, channelId, sid, badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.FAV_A_SONG_JSON, null);
        assertNotNull(favSongGateway.failureResponse);
        Assert.assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.CALLER_ERROR.getCode()));
    }
}
