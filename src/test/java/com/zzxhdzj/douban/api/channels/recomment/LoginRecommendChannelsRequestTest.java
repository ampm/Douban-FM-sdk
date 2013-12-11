package com.zzxhdzj.douban.api.channels.recomment;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
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
public class LoginRecommendChannelsRequestTest extends BaseAuthApiRequestTestCase{
        private LoginRecommendChannelRequest request;
        private String userId;

        @Before
        public void setUp() throws Exception {
            userId = "69077079";
            request = new LoginRecommendChannelRequest(userId,Constants.LOGIN_CHLS_URL,context);
        }

        @Test
        public void shouldHaveRequestUrl() {
            String url = request.getUrlString();
            assertThat(url, equalTo("http://douban.fm/j/explore/get_login_chls?uk=69077079"));
        }

}
