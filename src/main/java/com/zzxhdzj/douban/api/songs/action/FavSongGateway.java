package com.zzxhdzj.douban.api.songs.action;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.song.SongResp;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 11:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class FavSongGateway extends BaseGateway {

    public FavSongGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.R;
    }

    public void favASong(int currentChannelId, int songId, Callback callback) {
        apiGateway.makeRequest(new FavSongRequest(currentChannelId, songId), new FavSongApiResponseCallbacks(callback));
    }

    private class FavSongApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;
        public FavSongApiResponseCallbacks(Callback callback) {
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
