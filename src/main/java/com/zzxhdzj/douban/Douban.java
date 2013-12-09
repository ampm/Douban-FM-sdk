package com.zzxhdzj.douban;

import android.content.Context;
import android.content.SharedPreferences;
import com.zzxhdzj.douban.api.auth.AuthenGetCaptchaGateway;
import com.zzxhdzj.douban.api.auth.AuthenticationGateway;
import com.zzxhdzj.douban.api.channels.fixed.StaticChannelGateway;
import com.zzxhdzj.douban.api.channels.genre.GenreChannelGateway;
import com.zzxhdzj.douban.api.songs.SongsGateway;
import com.zzxhdzj.douban.modules.Channel;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.Song;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.util.Strings;

import java.util.ArrayList;

public class Douban {

    public String captchaImageUrl;
    public String captchaId;
    public ApiRespErrorCode apiRespErrorCode;
    public ArrayList<Channel> channels;
    public ArrayList<Channel> favChannels;
    public ArrayList<Channel> recChannels;
    public Channel recommendChannle;
    public ArrayList<Song> songs;
    private final ApiGateway apiGateway;

    public Douban() {
        apiGateway = new ApiGateway();
    }

    /**
     * 重构：
     * 去除成员变量？to be or not to be?
     * 1、如果去掉诸如  hotChannels变成返回，则对于调用放需要自己异步asyncTask方式调用，不方便。
     * 2、不去掉：每次调用new Douban实例，传个回调给api，api查询完毕通知调用方
     * 可方便取更新到的员变量数据
     * 调用方回调结束做成员变量的回收
     */
    public boolean isAuthenticated(Context context) {
        return !Strings.isEmptyOrWhitespace(getToken(context));
    }

    String getToken(Context context) {
        return context.getSharedPreferences(Constants.DOUBAN_AUTH, Context.MODE_PRIVATE).getString(Constants.TOKEN, "");
    }

    public void fecthCaptcha(Callback callback) {
        AuthenGetCaptchaGateway authenGetCaptchaGateway = new AuthenGetCaptchaGateway(this, apiGateway);
        authenGetCaptchaGateway.newCapthaId(callback);
    }

    public void login(Context context, LoginParams loginParams, Callback callback) {
        AuthenticationGateway authenticationGateway = new AuthenticationGateway(this, apiGateway, context);
        authenticationGateway.signIn(loginParams, callback);
    }

    public void queryHotChannles(int start, int limit, Callback callback) {
        StaticChannelGateway staticChannelGateway = new StaticChannelGateway(this, apiGateway);
        staticChannelGateway.fetchHotChannels(start, limit,callback);
    }

    public void queryFastChannles(int start, int limit, Callback callback) {
        StaticChannelGateway staticChannelGateway = new StaticChannelGateway(this, apiGateway);
        staticChannelGateway.fetchTrendingChannels(start, limit,callback);
    }

    public void queryChannlesByGenre(int genreid, int start, int limit, Callback callback) {
        GenreChannelGateway genreChannelGateway = new GenreChannelGateway(this, apiGateway);
        genreChannelGateway.fetchChannelsByGenreId(genreid, start, limit,callback);
    }
    public void songsToPlay(int channelId, int bitRate, Callback callback) {
        SongsGateway songsGateway = new SongsGateway(this, apiGateway);
        songsGateway.querySongsByChannelId(Constants.songType,channelId,bitRate,callback);
    }

    public void clear() {
        this.songs = null;
        this.channels = null;
        System.gc();
    }
}
