package com.zzxhdzj.douban.api.auth;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/26/13
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenGetCaptchaGatewayTest extends BaseGatewayTestCase {
    private AuthenGetCaptchaGateway authenGetCaptchaGateway;

    @Before
    public void setUp() {
        super.setUp();
        authenGetCaptchaGateway = new AuthenGetCaptchaGateway(douban, apiGateway);
    }

    //test#01
    @Test
    public void shouldMakeARemoteCallWhenFetchNewCaptchaId() {
        authenGetCaptchaGateway.newCaptchaId(new Callback());
        String urlString = apiGateway.getLatestRequest().getUrlString();
        assertThat(urlString, equalTo("http://douban.fm/j/new_captcha"));
    }

    @Test
    public void shouldReturnNewCaptchaId() throws Exception {
        authenGetCaptchaGateway.newCaptchaId(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NEW_CAPTCHA, null);
        assertThat(douban.captchaImageUrl, equalTo(Constants.CAPTCHA_URL + "&id=8Z9w6tODHEukHkAmBz52dWg4:en"));
    }
    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        authenGetCaptchaGateway.newCaptchaId(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        assertNotNull(authenGetCaptchaGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(),equalTo("500"));
    }
    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        authenGetCaptchaGateway.newCaptchaId(badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.NEW_CAPTCHA, null);
        assertNotNull(authenGetCaptchaGateway.failureResponse);
        assertThat(douban.apiRespErrorCode.getCode(),equalTo("-2"));
    }

}
