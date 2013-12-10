package com.zzxhdzj.douban.api.channels.action;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.channel.FavChannelResp;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelActionGateway extends BaseGateway{


    public ChannelActionGateway(Douban douban, ApiGateway apiGateway,RespType respType) {
        super(douban, apiGateway);
        this.respType = respType;
    }

    public void favAChannel(ChannelActionType favChannel, int channelId, Callback callback) {
        apiGateway.makeRequest(new ChannelActionRequest(favChannel,channelId),new ChannelActionApiResponseCallbacks(callback));
    }

    private class ChannelActionApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public ChannelActionApiResponseCallbacks(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            FavChannelResp favChannelResp = gson.fromJson(response.getResp(), FavChannelResp.class);
            if(isRespOk(favChannelResp)){
                callback.onSuccess();
            }else {
                onFailure(response);
            }
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
            callback.onFailure();
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
        }
    }
}
