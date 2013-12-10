package com.zzxhdzj.douban.api.songs.action;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseGateway;
import com.zzxhdzj.douban.modules.song.SongResp;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 9:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnFavSongGateway extends BaseGateway {


    public UnFavSongGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
    }

    public void unfavASong(int channelId, int sid, Callback callback) {
        apiGateway.makeRequest(new UnFavSongRequest(channelId, sid), new UnFavSongApiResponseCallbacks(callback));
    }

    private class UnFavSongApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public UnFavSongApiResponseCallbacks(Callback callback) {

            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            SongResp songResp = gson.fromJson(response.getResp(), SongResp.class);
            if(isRespOk(songResp)){
                douban.songs = songResp.songs;
                callback.onSuccess();
            }else callback.onFailure();
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
            callback.onFailure();
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
        }
    }
}
