package com.zzxhdzj.douban.api.channels.favorited;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseGateway;
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
public class FavoritedChannelGateway extends BaseGateway {

    public FavoritedChannelGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.STATUS;
    }

    public void fetchHotChannels(Callback callback) {
        apiGateway.makeRequest(new FavoritedChannelRequest(), new HotChannelCallback(callback));
    }

    public void fetchTrendingChannels(Callback callback) {
        apiGateway.makeRequest(new FavoritedChannelRequest(), new HotChannelCallback(callback));
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
            douban.channels = channelResp.channlesDatas.channels;
            callback.onSuccess();
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
        }
    }
}
