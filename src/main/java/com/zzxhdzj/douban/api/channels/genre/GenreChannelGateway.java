package com.zzxhdzj.douban.api.channels.genre;

import android.content.Context;
import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.channel.ChannelResp;
import com.zzxhdzj.http.*;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class GenreChannelGateway extends BaseApiGateway {

    public GenreChannelGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.STATUS;

    }

    public void fetchChannelsByGenreId(int genreId, int start, int limit, Callback callback) {
        apiGateway.makeRequest(new GenreChannelRequest(start, limit, genreId, Constants.GENRE_CHANNEL_URL,douban.getContext()), new GenreApiResponseCallbacks(callback));
    }

    private class GenreApiResponseCallbacks implements ApiResponseCallbacks<TextApiResponse> {
        private Callback callback;

        public GenreApiResponseCallbacks(Callback callback) {

            this.callback = callback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            ChannelResp channelResp = gson.fromJson(response.getResp(), ChannelResp.class);
            douban.channels = channelResp.channlesDatas.channels;
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
