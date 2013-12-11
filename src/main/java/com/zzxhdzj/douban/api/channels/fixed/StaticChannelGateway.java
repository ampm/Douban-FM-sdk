package com.zzxhdzj.douban.api.channels.fixed;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.ApiRespErrorCode;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.channel.ChannelResp;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaticChannelGateway extends BaseApiGateway {

    public StaticChannelGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.STATUS;
    }

    public void fetchHotChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(new StaticChannelRequest(start, limit, Constants.HOT_CHANNELS, douban.getContext()), new HotChannelCallback(callback));
    }

    public void fetchTrendingChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(new StaticChannelRequest(start, limit, Constants.TRENDING_CHANNELS, douban.getContext()), new HotChannelCallback(callback));
    }

    private class HotChannelCallback implements ApiResponseCallbacks<TextApiResponse> {

        private final Callback callback;

        public HotChannelCallback(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            ChannelResp channelResp = gson.fromJson(response.getResp(), ChannelResp.class);
            if (isRespOk(channelResp)) {
                douban.channels = channelResp.channlesDatas.channels;
                try {
                    callback.onSuccess();
                } catch (Exception onSuccessExp) {
                    douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.CALLER_ERROR_ON_SUCCESS);
                    onFailure(response);
                }
            } else {
                douban.apiRespErrorCode = new ApiRespErrorCode(respType, channelResp, channelResp.msg);
                onFailure(response);
            }

        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
            if (douban.apiRespErrorCode == null) {
                douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.INTERNAL_ERROR);
            }
            callback.onFailure();
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
            douban.clear();
        }
    }
}
