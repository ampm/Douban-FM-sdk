package com.zzxhdzj.douban.api.songs.action;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.modules.song.SongResp;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongActionGateway extends BaseApiGateway {



    public SongActionGateway(Douban douban, ApiGateway apiGateway,boolean isAuthRequire) {
        super(douban, apiGateway, RespType.R);
        this.isAuthRequire = isAuthRequire;
    }

    public void songAction(SongActionType songActionType, int currentChannelId, int songId, Callback callback) {
        apiGateway.makeRequest(new SongActionRequest(songActionType, currentChannelId, songId, douban.getContext()), new SongApiResponseCallbacks(callback, this, douban));
    }

    private class SongApiResponseCallbacks extends CommonTextApiResponseCallback {

        private SongResp songResp;

        public SongApiResponseCallbacks(Callback callback, BaseApiGateway songActionGateway, Douban douban) {
            super(callback, songActionGateway, douban);
        }

        @Override
        public void onSuccess(TextApiResponse response) {
            super.onSuccess(response);

            if (isRespOk(songResp)) {
                douban.songs = songResp.songs;
                callOnSuccess(response);
            } else {
                if(douban.mApiRespErrorCode!=null&&!douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())){
                    douban.mApiRespErrorCode = com.zzxhdzj.douban.api.base.ApiRespErrorCode.createBizError(songResp.getCode(respType), songResp.getMessage(respType));
                }
                onBizFailure(response);
            }
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            songResp = gson.fromJson(response.getResp(), SongResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(songResp)) {
                douban.songs = songResp.songs;
                return true;
            } else {
                if(douban.mApiRespErrorCode!=null&&!douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())){
                    douban.mApiRespErrorCode = com.zzxhdzj.douban.api.base.ApiRespErrorCode.createBizError(songResp.getCode(respType), songResp.getMessage(respType));
                }
                return false;
            }
        }
    }

}