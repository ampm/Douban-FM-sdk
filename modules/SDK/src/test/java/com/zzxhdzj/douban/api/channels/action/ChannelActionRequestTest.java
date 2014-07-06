package com.zzxhdzj.douban.api.channels.action;

import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelActionRequestTest extends BaseAuthApiRequestTestCase {
    private ChannelActionRequest request;
    private int channelId;

    @Before
    public void setUp() {
        channelId = 1;
    }

    @Test
    public void shouldHaveCorrectUrl() {
        request = new ChannelActionRequest(ChannelActionType.FAV_CHANNEL, channelId);
        String urlString = request.getUrlString();
        assertThat(urlString, equalTo("http://douban.fm/j/explore/fav_channel?cid=1"));
        request = new ChannelActionRequest(ChannelActionType.UNFAV_CHANNEL, channelId);
        urlString = request.getUrlString();
        assertThat(urlString, equalTo("http://douban.fm/j/explore/unfav_channel?cid=1"));
    }

}
