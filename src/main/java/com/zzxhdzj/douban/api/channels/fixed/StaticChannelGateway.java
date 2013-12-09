package com.zzxhdzj.douban.api.channels.fixed;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.ChannelRespRoot;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class StaticChannelGateway {
    private final ApiGateway apiGateway;
    public ApiResponse failureResponse;
    public Boolean onCompleteWasCalled;
    public String token;
    private final Douban douban;
    private Callback callback;


    public StaticChannelGateway(Douban douban, ApiGateway apiGateway) {
        this.douban = douban;
        this.apiGateway = apiGateway;
    }

    public void fetchHotChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(new StaticChannelRequest(start, limit, Constants.HOT_CHANNELS), new HotChannelCallback(callback));
    }

    public void fetchTrendingChannels(int start, int limit, Callback callback) {
        this.callback = callback;
        apiGateway.makeRequest(new StaticChannelRequest(start, limit, Constants.TRENDING_CHANNELS), new HotChannelCallback(callback));
    }

    private class HotChannelCallback implements ApiResponseCallbacks<TextApiResponse> {

        private final Callback callback;

        public HotChannelCallback(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            ChannelRespRoot channelRespRoot = gson.fromJson(response.getResp(), ChannelRespRoot.class);
            douban.channels = channelRespRoot.channlesDatas.channels;
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
