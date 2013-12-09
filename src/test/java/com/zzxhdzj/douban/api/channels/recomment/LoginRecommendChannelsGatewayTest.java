package com.zzxhdzj.douban.api.channels.recomment;

import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.mock.TestApiGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

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
@RunWith(RobolectricTestRunner.class)
public class LoginRecommendChannelsGatewayTest {

    private TestApiGateway apiGateway;
    private int userId;
    private LoginRecommendChannelGateway loginRecommendChannelGateway;
    private Douban douban;

    @Before
    public void setUp() {
        apiGateway = new TestApiGateway();
        userId = 69077079;
        douban = new Douban();
        loginRecommendChannelGateway = new LoginRecommendChannelGateway(douban, apiGateway);
    }

    @Test
    public void shouldFetchOneChannelBySpecificGenre() throws Exception {
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
}
