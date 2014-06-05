package com.zzxhdzj.douban.api.channels.query;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

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
public class FavoredChannelRequestTest extends BaseAuthApiRequestTestCase {
    private ApiRequest<TextApiResponse> request;
    private int start;
    private int limit;
    private String userId;
    private ArrayList<Integer> channelIds;

    @Before
    public void setUp() throws Exception {
        start = 1;
        limit = 1;
        userId = "69077079";
        channelIds = new ArrayList<Integer>();
        channelIds.add(2);
        channelIds.add(61);
        channelIds.add(9);
        channelIds.add(14);
    }

    @Test
    public void shouldHaveFavoredRequestUrl() {
        request = ChannelQueryRequest.getFavQueryRequest(context, "http://douban.fm/j/fav_channels");
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/fav_channels"));
    }

    @Test
    public void shouldHaveHotRequestUrl() {
        request = ChannelQueryRequest.getHotAndTrendingQueryRequest(start, limit, Constants.HOT_CHANNELS, context);
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/explore/hot_channels?start=1&limit=1"));
    }

    @Test
    public void shouldHaveLoginRecommendRequestUrl() {
        request = ChannelQueryRequest.getLoginRecommendChannelRequest(userId, Constants.LOGIN_CHLS_URL, context);
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/explore/get_login_chls?uk=69077079"));
    }
    @Test
    public void shouldHaveRecommendRequestUrl() {
        request = ChannelQueryRequest.getRecommendChannelRequest(channelIds, Constants.RECOMMEND_CHLS_URL, context);
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/explore/get_recommend_chl?orecs=2%7C61%7C9%7C14"));//2|61|9|14
    }
}
