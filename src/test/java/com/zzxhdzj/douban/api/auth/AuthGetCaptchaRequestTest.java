package com.zzxhdzj.douban.api.auth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/24/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class AuthGetCaptchaRequestTest {
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
