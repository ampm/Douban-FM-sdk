package com.zzxhdzj.douban.api.auth;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.ApiRespErrorCode;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.http.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/26/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenGetCaptchaGateway extends BaseApiGateway {

    public AuthenGetCaptchaGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
    }

    public void newCaptchaId(Callback callback) {
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
            try{
                callback.onSuccess();
            }catch (Exception onSuccessExp){
                douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.CALLER_ERROR_ON_SUCCESS);
                onFailure(response);
            }
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
            if(douban.apiRespErrorCode==null){
                douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.INTERNAL_ERROR);
            }
            callback.onFailure();
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
        }
    }
}
