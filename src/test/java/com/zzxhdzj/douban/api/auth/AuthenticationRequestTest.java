package com.zzxhdzj.douban.api.auth;

import com.zzxhdzj.douban.modules.LoginParams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/30/13
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class AuthenticationRequestTest {
    private AuthenticationRequest request;
    private LoginParams loginParams;

    @Before
    public void setUp() throws Exception {
        loginParams = LoginParams.createLoginParams("on", "radio", "cheese", "test@gmail.com", "password");
        request = new AuthenticationRequest(loginParams);
    }

    @Test
    public void shouldHaveRequestUrl() {
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/login"));
    }

}
