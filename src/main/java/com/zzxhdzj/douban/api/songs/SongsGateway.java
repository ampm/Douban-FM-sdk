package com.zzxhdzj.douban.api.songs;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.channel.ChannelResp;
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
public class SongsGateway extends BaseGateway {

    public SongsGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.R;
    }

    public void querySongsByChannelId(String songType, int channelId, int bitRate, Callback callback) {
        apiGateway.makeRequest(new SongsRequest(channelId, bitRate, songType), new SongApiResponseCallback(callback));
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
            douban.songs = songResp.songs;
            callback.onSuccess();
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
        }
    }
}
