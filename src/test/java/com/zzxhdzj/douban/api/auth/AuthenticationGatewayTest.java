package com.zzxhdzj.douban.api.auth;

import com.google.common.net.HttpHeaders;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.mock.TestApiGateway;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/29/13
 * Time: 12:21 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class AuthenticationGatewayTest {
    private TestApiGateway apiGateway;
    private LoginParams loginParams;
    private AuthenticationGateway authenticationGateway;
    private Douban douban;

    @Before
    public void setUp() {
        apiGateway = new TestApiGateway();
        douban = new Douban();
        authenticationGateway = new AuthenticationGateway(douban, apiGateway, Robolectric.application.getApplicationContext());
        loginParams = LoginParams.createLoginParams("on", "radio", "cheese", "test@gmail.com", "password");

    }

    //test#01
    @Test
    public void shouldMakeARemoteCallWhenSigningInWithCaptchaCode() {
        authenticationGateway.signIn(loginParams, new Callback());
        String urlString = apiGateway.getLatestRequest().getUrlString();
        assertThat(urlString, equalTo("http://douban.fm/j/login"));
    }

    //test#02
    @Test
    public void shouldSendLoginParams() {
        authenticationGateway.signIn(loginParams, new Callback());
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) apiGateway.getLatestRequest();
        assertThat(authenticationRequest, equalTo(new AuthenticationRequest(loginParams)));
    }

    //test#03
    @Test
    public void shouldReturnTrueSignedIn() throws Exception {
        assertThat(douban.isAuthenticated(Robolectric.application.getApplicationContext()), equalTo(false));
        authenticationGateway.signIn(loginParams, new Callback());
        Header[] header = new Header[1];
        header[0] = new BasicHeader(HttpHeaders.SET_COOKIE, "ue=\"xxxx@gmail.com\"; domain=.douban.com; expires=Mon, 24-Nov-2014 16:29:27 GMT,fmNlogin=\"y\"; path=/; domain=.douban.fm; expires=Tue, 24-Dec-2013 16:29:27 GMT,bid=\"jAT3l2qRKfc\"; path=/; domain=.douban.com; expires=Mon, 24-Nov-2014 16:29:27 GMT,dbcl2=\"69077079:YhfWsJoFZ00\"; path=/; domain=.douban.fm; expires=Tue, 24-Dec-2013 16:29:27 GMT; httponly,ck=\"10se\"; path=/; domain=.douban.fm");
        apiGateway.simulateTextResponse(200, TestResponses.AUTH_SUCCESS, header);
        assertThat(douban.isAuthenticated(Robolectric.application.getApplicationContext()), equalTo(true));
    }

    @Test
    public void shouldReturnFalseWhenSignedInWithCaptchaCodeError() throws Exception {
        assertThat(douban.isAuthenticated(Robolectric.application.getApplicationContext()), equalTo(false));
        authenticationGateway.signIn(loginParams, new Callback());
        Header[] header = new Header[0];
        apiGateway.simulateTextResponse(200, TestResponses.AUTH_ERROR, header);
        assertThat(douban.isAuthenticated(Robolectric.application.getApplicationContext()), equalTo(false));
        assertThat(douban.apiRespErrorCode.getCode(), equalTo(1011));
        assertThat(douban.apiRespErrorCode.getMsg(), equalTo("验证码不正确"));
    }


}
