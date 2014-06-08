package com.zzxhdzj.douban;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.api.auth.AuthenticationGateway;
import com.zzxhdzj.douban.api.auth.AuthorityGetCaptchaGateway;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.douban.api.channels.action.ChannelActionGateway;
import com.zzxhdzj.douban.api.channels.action.ChannelActionType;
import com.zzxhdzj.douban.api.channels.query.ChannelQueryGateway;
import com.zzxhdzj.douban.api.songs.SongsGateway;
import com.zzxhdzj.douban.db.DoubanDb;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.UserInfo;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.douban.modules.song.Song;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.Http;

import java.util.ArrayList;
import java.util.LinkedList;

public class Douban extends ApiInstance {

    public static SharedPreferences sharedPreferences;
    public static Context app;
    public String captchaImageUrl;
    public String captchaId;
    public ArrayList<Channel> channels;
    public ArrayList<Channel> favChannels;
    public ArrayList<Channel> recChannels;
    public Channel recommendChannel;
    public LinkedList<Song> songs;
    private final ApiGateway apiGateway;
    private Context context;
    public Object singleObject;

    public Douban(Context context) {
        this.context = context;
        apiGateway = new ApiGateway();
        sharedPreferences = context.getSharedPreferences(Constants.DOUBAN_AUTH, Context.MODE_PRIVATE);
    }
    public static void init(Context context){
        Http.initCookieManager(context);
        app = context;
        DoubanDb.getInstance(app).getDb(true);
    }

    /**
     * 获取验证码CaptchaId
     *s
     * @param callback
     */
    public void fetchCaptcha(Callback callback) {
        AuthorityGetCaptchaGateway authorityGetCaptchaGateway = new AuthorityGetCaptchaGateway(this, apiGateway);
        authorityGetCaptchaGateway.newCaptchaId(callback);
    }

    /**
     * 登录
     *
     * @param loginParams 登录信息userInfo
     * @param callback
     */
    public void login(LoginParams loginParams, Callback callback) {
        AuthenticationGateway authenticationGateway = new AuthenticationGateway(this, apiGateway);
        authenticationGateway.signIn(loginParams, callback);
    }

    /**
     * 查询最热频道 通过douban.channels获取
     *
     * @param start
     * @param limit
     * @param callback
     */
    public void queryHotChannels(int start, int limit, Callback callback) {
        ChannelQueryGateway staticChannelGateway = new ChannelQueryGateway(this, apiGateway);
        staticChannelGateway.fetchHotChannels(start, limit, callback);
    }

    /**
     * 上升最快，通过douban.channels获取
     *
     * @param start
     * @param limit
     * @param callback
     */
    public void queryTrendingChannles(int start, int limit, Callback callback) {
        ChannelQueryGateway staticChannelGateway = new ChannelQueryGateway(this, apiGateway);
        staticChannelGateway.fetchTrendingChannels(start, limit, callback);
    }

    /**
     * 根据流派查询频道，通过douban.channels获取
     *
     * @param genreId
     * @param start
     * @param limit
     * @param callback
     */
    public void queryChannlesByGenre(int genreId, int start, int limit, Callback callback) {
        ChannelQueryGateway genreChannelGateway = new ChannelQueryGateway(this, apiGateway);
        genreChannelGateway.fetchChannelsByGenreId(genreId, start, limit, callback);
    }



    /**
     * 获取登录后推荐频道,同时返回收藏频道和推荐频道，可通过douban.favChannels 和 douban.recChannels 获取
     *
     * @param userId
     * @param callback
     */
    public void recommendChannelsWhenLogin(String userId, Callback callback) {
        ChannelQueryGateway loginRecommendChannelGateway = new ChannelQueryGateway(this, apiGateway);
        loginRecommendChannelGateway.getLoginRecommendChannels(userId, callback);
    }

    /**
     * 求推荐：对应官方的"试试这些",可通过douban.channels获取
     *
     * @param channelIds
     * @param callback
     */
    public void recommendChannnels(ArrayList<Integer> channelIds, Callback callback) {
        ChannelQueryGateway recommendChannelsGateway = new ChannelQueryGateway(this, apiGateway);
        recommendChannelsGateway.getRecommendChannels(channelIds, callback);
    }

    /**
     * 收藏频道
     *
     * @param channelId
     * @param callback
     */
    public void favAvChannel(int channelId, Callback callback) {
        ChannelActionGateway channelActionGateway = new ChannelActionGateway(this, apiGateway);
        channelActionGateway.favAChannel(ChannelActionType.FAV_CHANNEL, channelId, callback);
    }

    /**
     * 取消收藏频道
     *
     * @param channelId
     * @param callback
     */
    public void unFavAChannel(int channelId, Callback callback) {
        ChannelActionGateway channelActionGateway = new ChannelActionGateway(this, apiGateway);
        channelActionGateway.favAChannel(ChannelActionType.FAV_CHANNEL, channelId, callback);
    }


    public void songsOfChannel(ReportType reportType, String currentSongId, int playTime, BitRate bitRate, Callback callback) {
        SongsGateway songsGateway = new SongsGateway(this, apiGateway);
        songsGateway.querySongsByChannelId(reportType,currentSongId,playTime, ChannelConstantIds.PRIVATE_CHANNEL, bitRate, callback);
    }




    public Context getContext() {
        return context;
    }


    public void clear() {
        if (!Constants.UNIT_TEST) {
            this.songs = null;
            this.channels = null;
            this.recChannels = null;
            this.favChannels = null;
            this.recommendChannel = null;
        }
    }

    public boolean isLogged() {
        return context.getSharedPreferences(Constants.DOUBAN_AUTH, Context.MODE_PRIVATE).getBoolean(PrefsConstant.LOGGED, false);
    }

    public UserInfo getUserInfo() {
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(sharedPreferences.getString(PrefsConstant.USER_KEY, null), UserInfo.class);
        return userInfo;
    }

    public void queryChannelInfo(String channelId, Callback callback) {
        ChannelQueryGateway channelQueryGateway = new ChannelQueryGateway(this,apiGateway);
        channelQueryGateway.queryChannelInfo(channelId,callback);
    }
}
