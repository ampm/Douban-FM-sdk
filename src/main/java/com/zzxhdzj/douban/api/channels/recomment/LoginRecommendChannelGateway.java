package com.zzxhdzj.douban.api.channels.recomment;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.LoginChannelsRoot;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRecommendChannelGateway {

    private final Douban douban;
    private final ApiGateway apiGateway;
    public ApiResponse failureResponse;
    public boolean onCompleteWasCalled;

    public LoginRecommendChannelGateway(Douban douban, ApiGateway apiGateway) {
        this.douban = douban;
        this.apiGateway = apiGateway;
    }

    public void query(int userId, Callback callback) {
        apiGateway.makeRequest(new LoginRecommendChannelRequest(userId, Constants.LOGIN_CHLS_URL), new LoginChannelsApiResponseCallbacks(callback));
    }

    private class LoginChannelsApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public LoginChannelsApiResponseCallbacks(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse textApiResponse) throws IOException {
            Gson gson = new Gson();
            LoginChannelsRoot loginChannelsRoot = gson.fromJson(textApiResponse.getResp(), LoginChannelsRoot.class);
            douban.favChannels = loginChannelsRoot.loginChannelsData.result.favoriteChannels;
            douban.recChannels = loginChannelsRoot.loginChannelsData.result.recommentChannels;
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
