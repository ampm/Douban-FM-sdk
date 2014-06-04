package com.zzxhdzj.douban.api.songs.action;

import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
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
public class SongActionRequestTest extends BaseAuthApiRequestTestCase {
    private SongActionRequest request;

    @Test
    public void shouldHaveRequestUrl() {
        request = new SongActionRequest(SongActionType.FAV, 0, 1939676,context);
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/mine/playlist?from=mainsite&kbps=64&type=r&channel=0&sid=1939676"));
        request = new SongActionRequest(SongActionType.UNFAV, 0, 1939676,context);
        url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/mine/playlist?from=mainsite&kbps=64&type=u&channel=0&sid=1939676"));
        request = new SongActionRequest(SongActionType.SKIP, 0, 1939676,context);
        url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/mine/playlist?from=mainsite&kbps=64&type=s&channel=0&sid=1939676"));
        request = new SongActionRequest(SongActionType.BAN, 0, 1939676,context);
        url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/mine/playlist?from=mainsite&kbps=64&type=b&channel=0&sid=1939676"));
    }

}
