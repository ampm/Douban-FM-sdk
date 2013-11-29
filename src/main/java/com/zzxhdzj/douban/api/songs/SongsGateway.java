package com.zzxhdzj.douban.api.songs;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.RespRoot;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongsGateway {
    private final ApiGateway apiGateway;
    private final Douban douban;
    public ApiResponse failureResponse;
    public Boolean onCompleteWasCalled;
    public SongsGateway(Douban douban , ApiGateway apiGateway) {
        this.apiGateway = apiGateway;
        this.douban = douban;
    }
    public void querySongsByChannelId(String songType, int channelId, int bitRate, Callback callback){
           apiGateway.makeRequest(new SongsRequest(channelId,bitRate,songType),new SongApiResponseCallback(callback));
    }

    private class SongApiResponseCallback implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public SongApiResponseCallback(Callback callback) {

            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            RespRoot respRoot = gson.fromJson(response.getResp(), RespRoot.class);
            douban.songs = respRoot.songs;
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
