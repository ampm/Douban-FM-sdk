package com.zzxhdzj.douban.api.channels.recomment;

import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.api.BaseGatewayTestCase;
import com.zzxhdzj.douban.api.mock.TestResponses;
import com.zzxhdzj.http.Callback;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
public class RecommendChannelsGatewayTest extends BaseGatewayTestCase {

    private ArrayList<Integer> channelIds;
    private RecommendChannelsGateway recommendChannelsGateway;

    @Before
    public void setUp() {
        super.setUp();
        channelIds = new ArrayList<Integer>();
        channelIds.add(2);
        channelIds.add(61);
        channelIds.add(9);
        channelIds.add(14);
        recommendChannelsGateway = new RecommendChannelsGateway(douban, apiGateway);
    }

    @Test
    public void shouldFetchOneRecommendChannel() throws Exception {
        recommendChannelsGateway.query(channelIds, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.REC_CHANNELS_JSON, null);
        assertNull(recommendChannelsGateway.failureResponse);
        assertTrue(recommendChannelsGateway.onCompleteWasCalled);
        assertNotNull(douban.recommendChannel);
        assertThat(douban.recommendChannel.name, equalTo("JUST FEELING"));
    }

    @Test
    public void shouldCallOnFailureWhenParseRespError() throws Exception {
        recommendChannelsGateway.query(channelIds, new Callback());
        apiGateway.simulateTextResponse(200, TestResponses.NULL_RESP, null);
        assertNotNull(recommendChannelsGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.INTERNAL_ERROR.getCode()));

    }

    @Test
    public void shouldCallOnFailureWhenCallerError() throws Exception {
        recommendChannelsGateway.query(channelIds,badCallback);
        apiGateway.simulateTextResponse(200, TestResponses.REC_CHANNELS_JSON, null);
        assertNotNull(recommendChannelsGateway.failureResponse);
        assertThat(douban.mApiRespErrorCode.getCode(), equalTo(ApiInternalError.CALLER_ERROR.getCode()));

    }
}
