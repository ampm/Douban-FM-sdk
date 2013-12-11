package com.zzxhdzj.douban.api.songs;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongsRequestTest extends BaseAuthApiRequestTestCase {
    private SongsRequest request;

    @Before
    public void setUp() throws Exception {
        int bitrate = 128;
        int channelId = 1;
        request = new SongsRequest(channelId, bitrate, Constants.songType,context);
    }

    @Test
    public void shouldHaveRequestUrl() {
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/mine/playlist?channel=1&kbps=128&type=n"));
    }
}
