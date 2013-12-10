package com.zzxhdzj.douban.api.songs.action;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnFavSongRequestTest {
    private UnFavSongRequest request;

    @Before
    public void setUp(){
        request = new UnFavSongRequest(0, 1939676);
    }
    @Test
    public void shouldHasRequestUrl(){
        String urlString = request.getUrlString();
        assertThat(urlString,equalTo("http://douban.fm/j/mine/playlist?type=u&from=mainsite&kbps=64&channel=0&sid=1939676"));
    }
}
