package com.zzxhdzj.douban.api.channels.recomment;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.douban.modules.channel.ChannelBuilder;
import com.zzxhdzj.douban.modules.channel.RecommendChannelsResp;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

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
        super(douban, apiGateway, RespType.STATUS);
        this.isAuthRequire = true;
    }

    public void query(ArrayList<Integer> channelIds, Callback callback) {
        apiGateway.makeRequest(new RecommendChannelRequest(channelIds, Constants.RECOMMEND_CHLS_URL, douban.getContext()),
                new RecommendApiResponseCallbacks(callback, this, douban));
    }

    private class RecommendApiResponseCallbacks extends CommonTextApiResponseCallback {

        private RecommendChannelsResp recommendChannelsResp;

        public RecommendApiResponseCallbacks(Callback bizCallback, BaseApiGateway gateway, ApiInstance apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            recommendChannelsResp = gson.fromJson(response.getResp(), RecommendChannelsResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(recommendChannelsResp)) {
                Channel channel = ChannelBuilder.aChannel()
                        .withId(recommendChannelsResp.recommendChannelData.result.id)
                        .withName(recommendChannelsResp.recommendChannelData.result.name)
                        .withCover(recommendChannelsResp.recommendChannelData.result.cover)
                        .withIntro(recommendChannelsResp.recommendChannelData.result.intro).build();
                douban.recommendChannel = channel;
                return true;
            } else {
                if(douban.mApiRespErrorCode==null){
                    douban.mApiRespErrorCode = com.zzxhdzj.douban.api.base.ApiRespErrorCode.createBizError(recommendChannelsResp.getCode(respType), recommendChannelsResp.getMessage(respType));
                }
                return false;
            }
        }
    }

}
