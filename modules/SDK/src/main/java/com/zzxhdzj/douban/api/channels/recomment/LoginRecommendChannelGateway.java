package com.zzxhdzj.douban.api.channels.recomment;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.modules.channel.LoginChannelsResp;
import com.zzxhdzj.http.*;
/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRecommendChannelGateway extends BaseApiGateway {


    public LoginRecommendChannelGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway,RespType.STATUS);
        this.isAuthRequire = true;
    }

    public void query(String userId, Callback callback) {
        apiGateway.makeRequest(new LoginRecommendChannelRequest(userId, Constants.LOGIN_CHLS_URL, douban.getContext()), new LoginChannelsApiResponseCallbacks(callback,this,douban));
    }

    private class LoginChannelsApiResponseCallbacks extends CommonTextApiResponseCallback {

        private LoginChannelsResp loginChannelsResp;

        public LoginChannelsApiResponseCallbacks(Callback bizCallback, BaseApiGateway gateway, ApiInstance apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            loginChannelsResp = gson.fromJson(response.getResp(), LoginChannelsResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if(isRespOk(loginChannelsResp)){
                douban.favChannels = loginChannelsResp.loginChannelsData.result.favoriteChannels;
                douban.recChannels = loginChannelsResp.loginChannelsData.result.recommentChannels;
                return true;
            }else {
                if(douban.mApiRespErrorCode == null || !douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())){
                    douban.mApiRespErrorCode = ApiRespErrorCode.createBizError(loginChannelsResp.getCode(respType), loginChannelsResp.getMessage(respType));
                }
                return false;
            }
        }
    }

}
