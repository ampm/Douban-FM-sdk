package com.zzxhdzj.douban.api.channels.query;


import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class FavoritedChannelGatewayTest extends BaseGatewayTestCase {
    ChannelQueryGateway queryGateway;
    private int limit;
    private int start;
    private String userId;
    private ArrayList<Integer> channelIds;
    private int genreId;

    @Before
    public void setUp() {
        super.setUp();
        queryGateway = new ChannelQueryGateway(douban, apiGateway);
        start = 1;
        limit = 1;
        userId = "69077079";
        channelIds = new ArrayList<Integer>();
        channelIds.add(2);
        channelIds.add(61);
        channelIds.add(9);
        channelIds.add(14);
        genreId = 335;
    }

    @Test
    public void shouldFetchOneHotChannelWithCorrectResp() throws Exception {
        queryGateway.fetchFavChannels(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.HOT_CHANNELS_JSON, null);
        assertNull(queryGateway.failureResponse);
        assertTrue(queryGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
    }


    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        queryGateway.fetchFavChannels(new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        assertNotNull(queryGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.INTERNAL_ERROR.getCode()));
    }

    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        queryGateway.fetchFavChannels(badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.HOT_CHANNELS_JSON, null);
        assertNotNull(queryGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.CALLER_ERROR.getCode()));

    }


    @Test
    public void shouldFetchOneTrendingChannelWithCorrectResp() throws Exception {
        queryGateway.fetchTrendingChannels(start, limit, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.FAST_CHANNELS_JSON, null);
        assertNull(queryGateway.failureResponse);
        assertTrue(queryGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
    }






    @Test
    public void shouldGetRecommendChannelsAfterLogin() throws Exception {
        queryGateway.getLoginRecommendChannels(userId, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.LOGIN_CHANNELS_JSON, null);
        TestCase.assertNull(queryGateway.failureResponse);
        TestCase.assertTrue(queryGateway.onCompleteWasCalled);
        assertNotNull(douban.favChannels);
        assertThat(douban.favChannels.size(), equalTo(1));
        assertThat(douban.favChannels.get(0).name, equalTo("工作学习"));
        assertNotNull(douban.recChannels);
        assertThat(douban.recChannels.size(), equalTo(2));
        assertThat(douban.recChannels.get(0).name, equalTo("户外"));
    }

    @Test
    public void shouldFetchOneRecommendChannel() throws Exception {
        queryGateway.getRecommendChannels(channelIds, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.SUGEST_CHANNELS_JSON, null);
        TestCase.assertNull(queryGateway.failureResponse);
        TestCase.assertTrue(queryGateway.onCompleteWasCalled);
        assertNotNull(douban.recommendChannel);
        assertThat(douban.recommendChannel.name, equalTo("JUST FEELING"));
    }
    @Test
    public void shouldFetchOneChannelBySpecificGenre() throws Exception {
        queryGateway.fetchChannelsByGenreId(genreId, start, limit, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.ROCK_CHANNELS_JSON, null);
        TestCase.assertNull(queryGateway.failureResponse);
        TestCase.assertTrue(queryGateway.onCompleteWasCalled);
        assertNotNull(douban.channels);
        assertThat(douban.channels.size(), equalTo(1));
        assertThat(douban.channels.get(0).name, equalTo("摇滚"));
    }



}
