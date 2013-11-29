package com.zzxhdzj.douban.api.songs;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.mock.TestApiGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
@RunWith(RobolectricTestRunner.class)
public class SongsGatewayTest {
    public TestApiGateway apiGateway;
    SongsGateway songsGateway;
    private Douban douban;

    @Before
    public void setUp() {
        apiGateway = new TestApiGateway();
        douban = new Douban();
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
}
