package com.zzxhdzj.douban.api.songs;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.Callback;
import junit.framework.TestCase;
import org.junit.Assert;
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
        songsGateway.querySongsByChannelId(Constants.songType, 1, 128, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.ROCK_CHANNELS_SONGS_JSON, null);
        assertNotNull(douban.songs);
        assertThat(douban.songs.size(), equalTo(2));
        assertThat(douban.songs.get(0).aid, equalTo("25779410"));
    }

    @Test
    public void shouldHaveCookie() throws Exception {
        songsGateway.querySongsByChannelId(Constants.songType, 1, 128, new Callback());
        ApiRequest apiRequest = apiGateway.getLatestRequest();
        TestCase.assertTrue(apiRequest.getHeaders().containsKey("Cookie"));
        Assert.assertThat(apiRequest.getHeaders().get("Cookie").toString(), equalTo(""));
    }

    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        songsGateway.querySongsByChannelId(Constants.songType, 1, 128, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        TestCase.assertNotNull(songsGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(), equalTo("500"));
    }

    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        songsGateway.querySongsByChannelId(Constants.songType, 1, 128,badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.ROCK_CHANNELS_SONGS_JSON, null);
        TestCase.assertNotNull(songsGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(), equalTo("-1"));
    }
}
