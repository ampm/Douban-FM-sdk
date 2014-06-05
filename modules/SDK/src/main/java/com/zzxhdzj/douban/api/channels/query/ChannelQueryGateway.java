package com.zzxhdzj.douban.api.channels.query;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.modules.channel.*;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelQueryGateway extends BaseApiGateway {

    public ChannelQueryGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway, RespType.STATUS);
        this.isAuthRequire = true;
    }

    public void fetchFavChannels(Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getFavQueryRequest(douban.getContext(), Constants.FAV_CHANNELS),
                new ChannelApiResponseCallback(callback, this, douban));
    }

    public void fetchHotChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getHotAndTrendingQueryRequest(start, limit, Constants.HOT_CHANNELS, douban.getContext()), new ChannelApiResponseCallback(callback, this, douban));
    }

    public void fetchTrendingChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getHotAndTrendingQueryRequest(start, limit, Constants.TRENDING_CHANNELS, douban.getContext()), new ChannelApiResponseCallback(callback, this, douban));
    }

    public void fetchChannelsByGenreId(int genreId, int start, int limit, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getQueryByGenreRequest(start, limit, genreId, Constants.GENRE_CHANNEL_URL, douban.getContext()), new ChannelApiResponseCallback(callback, this, douban));
    }

    public void getLoginRecommendChannels(String userId, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getLoginRecommendChannelRequest(userId, Constants.LOGIN_CHLS_URL, douban.getContext()), new LoginChannelsApiResponseCallbacks(callback, this, douban));
    }

    public void getRecommendChannels(ArrayList<Integer> channelIds, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getRecommendChannelRequest(channelIds, Constants.RECOMMEND_CHLS_URL, douban.getContext()),
                new RecommendApiResponseCallbacks(callback, this, douban));
    }

    private class ChannelApiResponseCallback extends CommonTextApiResponseCallback<Douban> {

        private ChannelResp channelResp;

        public ChannelApiResponseCallback(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }


        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            channelResp = gson.fromJson(response.getResp(), ChannelResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(channelResp)) {
                douban.channels = channelResp.channlesDatas.channels;
                return true;
            } else {
                if (douban.mApiRespErrorCode == null || !douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())) {
                    douban.mApiRespErrorCode = ApiRespErrorCode.createBizError(channelResp.getCode(respType), channelResp.getMessage(respType));
                }
                return false;
            }
        }


    }

    private class RecommendApiResponseCallbacks extends CommonTextApiResponseCallback<Douban> {

        private RecommendChannelsResp recommendChannelsResp;

        public RecommendApiResponseCallbacks(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            recommendChannelsResp = gson.fromJson(response.getResp(), RecommendChannelsResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(recommendChannelsResp)) {
                Channel channel = ChannelBuilder.aChannel()
                        .withId(recommendChannelsResp.recommendChannelData.result.id)
                        .withName(recommendChannelsResp.recommendChannelData.result.name)
                        .withCover(recommendChannelsResp.recommendChannelData.result.cover)
                        .withIntro(recommendChannelsResp.recommendChannelData.result.intro).build();
                douban.recommendChannel = channel;
                return true;
            } else {
                if (douban.mApiRespErrorCode == null || !douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())) {
                    douban.mApiRespErrorCode = com.zzxhdzj.douban.api.base.ApiRespErrorCode.createBizError(recommendChannelsResp.getCode(respType), recommendChannelsResp.getMessage(respType));
                }
                return false;
            }
        }
    }

    private class LoginChannelsApiResponseCallbacks extends CommonTextApiResponseCallback<Douban> {

        private LoginChannelsResp loginChannelsResp;

        public LoginChannelsApiResponseCallbacks(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            loginChannelsResp = gson.fromJson(response.getResp(), LoginChannelsResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(loginChannelsResp)) {
                douban.favChannels = loginChannelsResp.loginChannelsData.result.favoriteChannels;
                douban.recChannels = loginChannelsResp.loginChannelsData.result.recommentChannels;
                return true;
            } else {
                if (douban.mApiRespErrorCode == null || !douban.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())) {
                    douban.mApiRespErrorCode = ApiRespErrorCode.createBizError(loginChannelsResp.getCode(respType), loginChannelsResp.getMessage(respType));
                }
                return false;
            }
        }
    }

}
