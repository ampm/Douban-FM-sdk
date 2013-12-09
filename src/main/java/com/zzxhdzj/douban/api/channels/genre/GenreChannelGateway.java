package com.zzxhdzj.douban.api.channels.genre;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.ChannelRespRoot;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class GenreChannelGateway {
    private final Douban douban;
    private final ApiGateway apiGateway;
    public ApiResponse failureResponse;
    public Boolean onCompleteWasCalled;
    public GenreChannelGateway(Douban douban, ApiGateway apiGateway) {

        this.douban = douban;
        this.apiGateway = apiGateway;
    }

    public void fetchChannelsByGenreId(int genreId, int start, int limit, Callback callback) {
        apiGateway.makeRequest(new GenreChannelRequest(start, limit, genreId, Constants.GENRE_CHANNEL_URL), new GenreApiResponseCallbacks(callback));
    }

    private class GenreApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public GenreApiResponseCallbacks(Callback callback) {

            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            ChannelRespRoot channelRespRoot = gson.fromJson(response.getResp(), ChannelRespRoot.class);
            douban.channels = channelRespRoot.channlesDatas.channels;
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
