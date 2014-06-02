package com.zzxhdzj.douban.api.auth;

import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.zzxhdzj.douban.CacheConstant;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.LoginResp;
import com.zzxhdzj.douban.modules.UserInfo;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationGateway<T extends ApiInstance> extends BaseApiGateway {


    public AuthenticationGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway, RespType.R);
    }

    public void signOut() {
    }

    public void signIn(LoginParams login, Callback responseCallback) {
        apiGateway.makeRequest(new AuthenticationRequest(login, douban.getContext()),
                new AuthenticationApiResponseCallback(responseCallback, this, douban));
    }

    private void cacheUserInfo(UserInfo userInfo) {
        Gson gson = new Gson();
        SharedPreferences.Editor edit = douban.getDoubanSharedPreferences().edit();
        edit.putString(CacheConstant.USER_KEY, gson.toJson(userInfo));
        edit.commit();
    }

    private void markAsLogged() {
        douban.getDoubanSharedPreferences().edit().putBoolean(CacheConstant.LOGGED, true).commit();
    }

    private class AuthenticationApiResponseCallback extends CommonTextApiResponseCallback<Douban> {
        private LoginResp loginResp;

        public AuthenticationApiResponseCallback(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            loginResp = gson.fromJson(response.getResp(), LoginResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(loginResp)) {
                markAsLogged();
                cacheUserInfo(loginResp.userInfo);
                return true;
            } else {
                if(douban.mApiRespErrorCode==null){
                    douban.mApiRespErrorCode = ApiRespErrorCode.createBizError(loginResp.getCode(respType), loginResp.getMessage(respType));
                }
                return false;
            }
        }

        @Override
        public void onComplete() {
            super.onComplete();
            douban.clear();
        }
    }

}
