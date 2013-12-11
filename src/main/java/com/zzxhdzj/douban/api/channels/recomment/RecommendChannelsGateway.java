package com.zzxhdzj.douban.api.channels.recomment;

import android.content.Context;
import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.douban.modules.channel.ChannelBuilder;
import com.zzxhdzj.douban.modules.channel.RecommendChannelsResp;
import com.zzxhdzj.http.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendChannelsGateway extends BaseApiGateway {

    public RecommendChannelsGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.STATUS;

    }

    public void query(ArrayList channelIds, Callback callback) {
        apiGateway.makeRequest(new RecommendChannelRequest(channelIds, Constants.REC_CHLS_URL,douban.getContext()), new RecommendApiResponseCallbacks(callback));
    }

    private class RecommendApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public RecommendApiResponseCallbacks(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse textApiResponse) throws IOException {
            Gson gson = new Gson();
            RecommendChannelsResp rc = gson.fromJson(textApiResponse.getResp(), RecommendChannelsResp.class);
            Channel channel = ChannelBuilder.aChannel()
                    .withId(rc.recommendChannelData.result.id)
                    .withName(rc.recommendChannelData.result.name)
                    .withCover(rc.recommendChannelData.result.cover)
                    .withIntro(rc.recommendChannelData.result.intro).build();
            douban.recommendChannel = channel;
            callback.onSuccess();
        }

        @Override
        public void onFailure(ApiResponse apiResponse) {
           failureResponse = apiResponse;
        }

        @Override
        public void onComplete() {
           onCompleteWasCalled = true;
        }
    }
}
