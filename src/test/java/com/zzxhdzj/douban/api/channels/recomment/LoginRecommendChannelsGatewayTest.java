package com.zzxhdzj.douban.api.channels.recomment;

import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.Callback;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRecommendChannelsGatewayTest extends BaseGatewayTestCase {

    private int userId;
    private LoginRecommendChannelGateway loginRecommendChannelGateway;

    @Before
    public void setUp() {
        super.setUp();
        userId = 69077079;
        loginRecommendChannelGateway = new LoginRecommendChannelGateway(douban, apiGateway);
    }

    @Test
    public void shouldGetRecommendChannelsAfterLogin() throws Exception {
        loginRecommendChannelGateway.query(userId, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.LOGIN_CHANNELS_JSON, null);
        assertNull(loginRecommendChannelGateway.failureResponse);
        assertTrue(loginRecommendChannelGateway.onCompleteWasCalled);
        assertNotNull(douban.favChannels);
        assertThat(douban.favChannels.size(), equalTo(1));
        assertThat(douban.favChannels.get(0).name, equalTo("工作学习"));
        assertNotNull(douban.recChannels);
        assertThat(douban.recChannels.size(), equalTo(2));
        assertThat(douban.recChannels.get(0).name, equalTo("户外"));
    }
    @Test
    public void shouldHaveCookie() throws Exception {
        loginRecommendChannelGateway.query(userId, new Callback());
        ApiRequest apiRequest = apiGateway.getLatestRequest();
        TestCase.assertTrue(apiRequest.getHeaders().containsKey("Cookie"));
        assertThat(apiRequest.getHeaders().get("Cookie").toString(), equalTo(""));
    }
}
