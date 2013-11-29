package com.zzxhdzj.douban.api.auth;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/26/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenGetCaptchaGateway {
    private final Douban douban;
    private ApiGateway apiGateway;
    public ApiResponse failureResponse;

    public AuthenGetCaptchaGateway(Douban douban, ApiGateway apiGateway) {
        this.douban = douban;
        this.apiGateway = apiGateway;
    }

    public void newCapthaId(Callback callback) {
        apiGateway.makeRequest(new AuthGetCaptchaRequest(), new AuthenGetCaptchaCallback(callback));
    }

    private class AuthenGetCaptchaCallback implements ApiResponseCallbacks<TextApiResponse> {

        private final Callback callback;

        public AuthenGetCaptchaCallback(Callback callback) {

            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            Object obj = gson.fromJson(response.getResp(), Object.class);
            douban.captchaId = obj.toString();
            douban.captchaImageUrl = Constants.CAPTCHA_URL + "&id=" + douban.captchaId;
            callback.onSuccess();
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
        }

        @Override
        public void onComplete() {
        }
    }
}
