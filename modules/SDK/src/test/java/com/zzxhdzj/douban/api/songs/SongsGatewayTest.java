package com.zzxhdzj.douban.api.songs;

import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.ChannelConstantIds;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongsGatewayTest extends BaseGatewayTestCase {
    SongsGateway songsGateway;

    @Before
    public void setUp() {
        super.setUp();
        songsGateway = new SongsGateway(douban, apiGateway);
    }

    @Test
    public void shouldReturnSongsOfChannel() throws Exception {
        songsGateway.querySongsByChannelId(ReportType.NEXT_QUEUE, "1", 10, ChannelConstantIds.PRIVATE_CHANNEL,BitRate.HIGH, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.ROCK_CHANNELS_SONGS_JSON, null);
        assertNotNull(Douban.songs);
        assertThat(Douban.songs.size(), equalTo(2));
        assertThat(Douban.songs.get(0).aid, equalTo("25779410"));
    }


    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        songsGateway.querySongsByChannelId(ReportType.NEXT_QUEUE, "1", 10, ChannelConstantIds.PRIVATE_CHANNEL, BitRate.HIGH, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        TestCase.assertNotNull(songsGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.INTERNAL_ERROR.getCode()));
    }

    @Test
    public void shouldCallOnFailureWhenCallerErrorWithABadCallback() throws Exception {
        songsGateway.querySongsByChannelId(ReportType.NEXT_QUEUE, "1", 10, ChannelConstantIds.PRIVATE_CHANNEL, BitRate.HIGH,badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.ROCK_CHANNELS_SONGS_JSON, null);
        TestCase.assertNotNull(songsGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.CALLER_ERROR.getCode()));
    }
}
