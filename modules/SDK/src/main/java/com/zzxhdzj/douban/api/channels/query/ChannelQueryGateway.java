package com.zzxhdzj.douban.api.channels.query;

import com.google.gson.Gson;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.db.tables.ChannelTypes;
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


    public static final Gson GSON = new Gson();

    public ChannelQueryGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway, RespType.STATUS);
        this.isAuthRequire = true;
    }

    public void queryChannelInfo(String channelId, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getASingleQueryRequest(channelId,Constants.CHANNEL_DETAILS),
                new SingleChannelApiResponseCallback(callback, this, douban));
    }

    public void fetchFavChannels(Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getFavQueryRequest(Constants.FAV_CHANNELS),
                new ChannelsApiResponseCallback(callback, this, douban,null));
    }

    public void fetchHotChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest
				.getHotAndTrendingQueryRequest(start, limit, Constants.HOT_CHANNELS),
				new ChannelsApiResponseCallback(callback, this, douban, ChannelTypes.Hits));
    }

    public void fetchTrendingChannels(int start, int limit, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getHotAndTrendingQueryRequest(start, limit, Constants.TRENDING_CHANNELS),
                new ChannelsApiResponseCallback(callback, this, douban,ChannelTypes.Trending));
    }

    public void fetchChannelsByGenreId(int genreId, int start, int limit, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getQueryByGenreRequest(start, limit, genreId, Constants.GENRE_CHANNEL_URL),
                new ChannelsApiResponseCallback(callback, this, douban,null));
    }

    public void getLoginRecommendChannels(String userId, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getLoginRecommendChannelRequest(userId, Constants.LOGIN_CHLS_URL),
                new LoginChannelsApiResponseCallbacks(callback, this, douban));
    }

    public void getRecommendChannels(ArrayList<Integer> channelIds, Callback callback) {
        apiGateway.makeRequest(ChannelQueryRequest.getRecommendChannelRequest(channelIds, Constants.RECOMMEND_CHLS_URL),
                new RecommendApiResponseCallbacks(callback, this, douban));
    }

    private class ChannelsApiResponseCallback extends CommonTextApiResponseCallback<Douban> {

        private final ChannelTypes category;
        private ChannelResp channelResp;

        public ChannelsApiResponseCallback(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance,ChannelTypes category) {
            super(bizCallback, gateway, apiInstance);
            this.category = category;
        }


        @Override
        public void _extractRespData(TextApiResponse response) {
            channelResp = GSON.fromJson(response.getResp(), ChannelResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(channelResp)) {
                if(category!=null){
                    douban.channels = new ArrayList<Channel>();
                    for (Channel channel:channelResp.channleData.channels){
                        channel.setCategory(category.getIndex());
                        douban.channels.add(channel);
                    }
                }else {
                    douban.channels = channelResp.channleData.channels;
                }
                return true;
            } else {
                detectAuthError(channelResp);
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
//            Gson gson = new Gson();
            recommendChannelsResp = GSON.fromJson(response.getResp(), RecommendChannelsResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(recommendChannelsResp)) {
                Channel channel = ChannelBuilder.aChannel()
                        .withId(recommendChannelsResp.recommendChannelData.result.id)
                        .withName(recommendChannelsResp.recommendChannelData.result.name)
                        .withCover(recommendChannelsResp.recommendChannelData.result.cover)
                        .withIntro(recommendChannelsResp.recommendChannelData.result.intro)
                        .withCategory(ChannelTypes.Try.getIndex())
                        .build();
                douban.recommendChannel = channel;

                return true;
            } else {
                detectAuthError(recommendChannelsResp);
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
//            Gson gson = new Gson();
            loginChannelsResp = GSON.fromJson(response.getResp(), LoginChannelsResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(loginChannelsResp)) {
                douban.favChannels = loginChannelsResp.loginChannelsData.result.favoriteChannels;
                douban.recChannels = loginChannelsResp.loginChannelsData.result.recommentChannels;
                return true;
            } else {
                detectAuthError(loginChannelsResp);
                return false;
            }
        }
    }

    private class SingleChannelApiResponseCallback extends CommonTextApiResponseCallback<Douban> {
        private ChannelDetailResp channelDetailResp;

        public SingleChannelApiResponseCallback(Callback bizCallback, ChannelQueryGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
//            Gson gson = new Gson();
            channelDetailResp = GSON.fromJson(response.getResp(), ChannelDetailResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(channelDetailResp)) {
                douban.singleObject = channelDetailResp.channelDetailData.channel;
                return true;
            } else {
                detectAuthError(channelDetailResp);
                return false;
            }
        }


    }
}
