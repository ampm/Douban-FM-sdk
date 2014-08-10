package com.zzxhdzj.douban.api.channels.action;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.modules.channel.FavChannelResp;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelActionGateway extends BaseApiGateway {


    public static final Gson GSON = new Gson();

    public ChannelActionGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway, RespType.STATUS);
        this.isAuthRequire = true;
    }

    public void favAChannel(ChannelActionType favChannel, int channelId, Callback callback) {
        apiGateway.makeRequest(new ChannelActionRequest(favChannel, channelId), new ChannelActionApiResponseCallbacks(callback, this, douban));
    }

    private class ChannelActionApiResponseCallbacks extends CommonTextApiResponseCallback<Douban> {

        private FavChannelResp favChannelResp;

        public ChannelActionApiResponseCallbacks(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            favChannelResp = GSON.fromJson(response.getResp(), FavChannelResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(favChannelResp)) {
                callOnSuccess(response);
                return true;
            } else {
                if(douban.mApiRespErrorCode == null || !douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())){
                    douban.mApiRespErrorCode = ApiRespErrorCode.createBizError(favChannelResp.getCode(respType), favChannelResp.getMessage(respType));
                }
                return false;
            }
        }

    }

}
