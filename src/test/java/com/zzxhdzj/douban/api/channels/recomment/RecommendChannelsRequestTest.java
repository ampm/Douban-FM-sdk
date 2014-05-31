package com.zzxhdzj.douban.api.channels.recomment;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendChannelsRequestTest extends BaseAuthApiRequestTestCase {
        private RecommendChannelRequest request;
        private ArrayList<Integer> channelIds;

        @Before
        public void setUp() throws Exception {
            channelIds = new ArrayList<Integer>();
            channelIds.add(2);
            channelIds.add(61);
            channelIds.add(9);
            channelIds.add(14);
            request = new RecommendChannelRequest(channelIds,Constants.RECOMMEND_CHLS_URL,context);
        }

        @Test
        public void shouldHaveRequestUrl() {
            String url = request.getUrlString();
            assertThat(url, equalTo("http://douban.fm/j/explore/get_recommend_chl?orecs=2%7C61%7C9%7C14"));//2|61|9|14
        }

}
