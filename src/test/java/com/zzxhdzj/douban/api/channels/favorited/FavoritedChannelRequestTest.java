package com.zzxhdzj.douban.api.channels.favorited;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class FavoritedChannelRequestTest {
    private FavoritedChannelRequest request;

    @Before
    public void setUp() throws Exception {
        request = new FavoritedChannelRequest();
    }

    @Test
    public void shouldHaveRequestUrl() {
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/fav_channels"));
    }
}
