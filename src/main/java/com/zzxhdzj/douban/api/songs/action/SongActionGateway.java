package com.zzxhdzj.douban.api.songs.action;

import android.content.Context;
import com.google.gson.Gson;
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

    private final int currentChannelId;
    private final int songId;

    public SongActionGateway(Douban douban, ApiGateway apiGateway,int currentChannelId, int songId) {
        super(douban, apiGateway);
        this.currentChannelId = currentChannelId;
        this.songId = songId;
        this.respType = RespType.R;
    }

    public void songAction(SongActionType songActionType, Callback callback) {
        apiGateway.makeRequest(new SongActionRequest(songActionType,currentChannelId, songId,douban.getContext()), new SongApiResponseCallbacks(callback));
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
            if(isRespOk(songResp)){
                douban.songs = songResp.songs;
                callback.onSuccess();
            }else onFailure(textApiResponse);
        }

        @Override
        public void onFailure(ApiResponse apiResponse) {
            failureResponse = apiResponse;
            callback.onFailure();
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
        }
    }
}