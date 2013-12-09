package com.zzxhdzj.douban.api.songs.action;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class FavSongRequestTest {
    private FavSongRequest request;

    @Before
    public void setUp() throws Exception {
        request = new FavSongRequest(0, 1939676);
    }


    @Test
    public void shouldHaveRequestUrl() {
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/mine/playlist?type=r&from=mainsite&kbps=64&channel=0&sid=1939676"));
    }

}
