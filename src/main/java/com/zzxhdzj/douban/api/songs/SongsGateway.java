package com.zzxhdzj.douban.api.songs;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.ApiRespErrorCode;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.song.SongResp;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongsGateway extends BaseApiGateway {

    public SongsGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.R;
    }

    public void querySongsByChannelId(String songType, int channelId, int bitRate, Callback callback) {
        apiGateway.makeRequest(new SongsRequest(channelId, bitRate, songType, douban.getContext()), new SongApiResponseCallback(callback));
    }

    private class SongApiResponseCallback implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public SongApiResponseCallback(Callback callback) {

            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            SongResp songResp = gson.fromJson(response.getResp(), SongResp.class);
            if (isRespOk(songResp)) {
                douban.songs = songResp.songs;
                try {
                    callback.onSuccess();
                } catch (Exception onSuccessExp) {
                    douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.CALLER_ERROR_ON_SUCCESS);
                    onFailure(response);
                }
            } else {
                douban.apiRespErrorCode = new ApiRespErrorCode(respType, songResp, songResp.msg);
                onFailure(response);
            }


        }

        @Override
        public void onFailure(ApiResponse apiResponse) {
            failureResponse = apiResponse;
            if(douban.apiRespErrorCode==null){
                douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.INTERNAL_ERROR);
            }
            callback.onFailure();
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
            douban.clear();
        }
    }
}
