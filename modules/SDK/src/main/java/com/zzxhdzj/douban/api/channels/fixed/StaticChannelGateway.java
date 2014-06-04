package com.zzxhdzj.douban.api.channels.fixed;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.modules.channel.ChannelResp;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaticChannelGateway extends BaseApiGateway {

    public StaticChannelGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway, RespType.STATUS);
    }

    public void fetchHotChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(new StaticChannelRequest(start, limit, Constants.HOT_CHANNELS, douban.getContext()), new HotChannelCallback(callback, this, douban));
    }

    public void fetchTrendingChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(new StaticChannelRequest(start, limit, Constants.TRENDING_CHANNELS, douban.getContext()), new HotChannelCallback(callback, this, douban));
    }

    private class HotChannelCallback extends CommonTextApiResponseCallback {

        private ChannelResp channelResp;

        public HotChannelCallback(Callback bizCallback, BaseApiGateway gateway, ApiInstance apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            channelResp = gson.fromJson(response.getResp(), ChannelResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(channelResp)) {
                douban.channels = channelResp.channlesDatas.channels;
                return true;
            } else {
                if(douban.mApiRespErrorCode == null || !douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())){
                    douban.mApiRespErrorCode = ApiRespErrorCode.createBizError(channelResp.getCode(respType), channelResp.getMessage(respType));
                }
                return false;
            }
        }
    }

}
