package com.zzxhdzj.douban.api.auth;

import com.zzxhdzj.douban.api.BaseAuthApiRequestTestCase;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.LoginParamsBuilder;
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
 * Date: 10/30/13
 * Time: 12:43 AM
 * To change this template use File | Settings | File Templates.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class AuthenticationRequestTest extends BaseAuthApiRequestTestCase {
    private AuthenticationRequest request;
    private LoginParams loginParams;

    @Before
    public void setUp() throws Exception {
        loginParams = LoginParamsBuilder.aLoginParams()
                .withRemember("on")
                .withSource("radio")
                .withCaptcha("cheese")
                .withLoginMail("test@gmail.com")
                .withPassword("password")
                .build();
        request = new AuthenticationRequest(loginParams);

    }

    @Test
    public void shouldHaveRequestUrl() {
        String url = request.getUrlString();
        assertThat(url, equalTo("http://douban.fm/j/login"));
    }


}
