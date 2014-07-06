package com.zzxhdzj.douban.api.auth;

import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/24/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class AuthGetCaptchaRequestTest extends BaseAuthApiRequestTestCase{
    private AuthGetCaptchaRequest request;
    @Before
    public void setUp() throws Exception {
        request = new AuthGetCaptchaRequest();
    }
    @Test
    public void shouldHaveRequestUrl() {
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/new_captcha"));
    }
}
