package com.zzxhdzj.douban.api.songs.action;

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
 * Date: 12/10/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongActionGateway extends BaseApiGateway {


    public SongActionGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        this.respType = RespType.R;
    }

    public void songAction(SongActionType songActionType, int currentChannelId, int songId, Callback callback) {
        apiGateway.makeRequest(new SongActionRequest(songActionType, currentChannelId, songId, douban.getContext()), new SongApiResponseCallbacks(callback));
    }

    private class SongApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public SongApiResponseCallbacks(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse textApiResponse) throws IOException {
            Gson gson = new Gson();
            SongResp songResp = gson.fromJson(textApiResponse.getResp(), SongResp.class);
            if (isRespOk(songResp)) {
                douban.songs = songResp.songs;
                try {
                    callback.onSuccess();
                } catch (Exception onSuccessExp) {
                    douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.CALLER_ERROR_ON_SUCCESS);
                    onFailure(textApiResponse);
                }
            } else {
                douban.apiRespErrorCode = new ApiRespErrorCode(respType, songResp, songResp.msg);
                onFailure(textApiResponse);
            }
        }

        @Override
        public void onFailure(ApiResponse apiResponse) {
            failureResponse = apiResponse;
            if (douban.apiRespErrorCode == null) {
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