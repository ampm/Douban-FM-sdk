package com.zzxhdzj.douban.api.channels.recomment;

import com.zzxhdzj.douban.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class LoginRecommendChannelsRequestTest {
        private LoginRecommendChannelRequest request;
        private int userId;

        @Before
        public void setUp() throws Exception {
            userId = 69077079;
            request = new LoginRecommendChannelRequest(userId,Constants.LOGIN_CHLS_URL);
        }

        @Test
        public void shouldHaveRequestUrl() {
            String url = request.getUrlString();
            assertThat(url, equalTo("http://douban.fm/j/explore/get_login_chls?uk=69077079"));
        }

}
