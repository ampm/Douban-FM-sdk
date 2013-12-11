package com.zzxhdzj.douban.api.channels.recomment;

import android.content.Context;
import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.channel.LoginChannelsResp;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRecommendChannelGateway extends BaseApiGateway {


    public LoginRecommendChannelGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.STATUS;
    }

    public void query(int userId, Callback callback) {
        apiGateway.makeRequest(new LoginRecommendChannelRequest(userId, Constants.LOGIN_CHLS_URL,douban.getContext()), new LoginChannelsApiResponseCallbacks(callback));
    }

    private class LoginChannelsApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public LoginChannelsApiResponseCallbacks(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse textApiResponse) throws IOException {
            Gson gson = new Gson();
            LoginChannelsResp loginChannelsResp = gson.fromJson(textApiResponse.getResp(), LoginChannelsResp.class);
            douban.favChannels = loginChannelsResp.loginChannelsData.result.favoriteChannels;
            douban.recChannels = loginChannelsResp.loginChannelsData.result.recommentChannels;
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
