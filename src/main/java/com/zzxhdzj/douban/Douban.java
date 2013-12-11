package com.zzxhdzj.douban;

import android.content.Context;
import android.content.SharedPreferences;
import com.zzxhdzj.douban.api.auth.AuthenGetCaptchaGateway;
import com.zzxhdzj.douban.api.auth.AuthenticationGateway;
import com.zzxhdzj.douban.api.channels.fixed.StaticChannelGateway;
import com.zzxhdzj.douban.api.channels.genre.GenreChannelGateway;
import com.zzxhdzj.douban.api.songs.SongsGateway;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.douban.modules.song.Song;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.util.Strings;
import java.util.ArrayList;

public class Douban {

    public static SharedPreferences sharedPreferences;
    public String captchaImageUrl;
    public String captchaId;
    public ApiRespErrorCode apiRespErrorCode;
    public ArrayList<Channel> channels;
    public ArrayList<Channel> favChannels;
    public ArrayList<Channel> recChannels;
    public Channel recommendChannel;
    public ArrayList<Song> songs;
    private final ApiGateway apiGateway;
    private Context context;

    public Douban(Context context) {
        this.context = context;
        apiGateway = new ApiGateway();
        sharedPreferences = context.getSharedPreferences(Constants.DOUBAN_AUTH, Context.MODE_PRIVATE);
    }


    public boolean isAuthenticated() {
        return !Strings.isEmptyOrWhitespace(getCookie(context));
    }
    public static String getCookie(Context context) {
        return sharedPreferences.getString(Constants.COOKIE, "");
    }

    /**
     *
     * @param callback
     */
    public void fecthCaptcha(Callback callback) {
        AuthenGetCaptchaGateway authenGetCaptchaGateway = new AuthenGetCaptchaGateway(this, apiGateway);
        authenGetCaptchaGateway.newCapthaId(callback);
    }

    public void login(Context context, LoginParams loginParams, Callback callback) {
        AuthenticationGateway authenticationGateway = new AuthenticationGateway(this, apiGateway);
        authenticationGateway.signIn(loginParams, callback);
    }

    public void queryHotChannles(int start, int limit, Callback callback) {
        StaticChannelGateway staticChannelGateway = new StaticChannelGateway(this, apiGateway);
        staticChannelGateway.fetchHotChannels(start, limit, callback);
    }

    public void queryFastChannles(int start, int limit, Callback callback) {
        StaticChannelGateway staticChannelGateway = new StaticChannelGateway(this, apiGateway);
        staticChannelGateway.fetchTrendingChannels(start, limit, callback);
    }

    public void queryChannlesByGenre(int genreid, int start, int limit, Callback callback) {
        GenreChannelGateway genreChannelGateway = new GenreChannelGateway(this, apiGateway);
        genreChannelGateway.fetchChannelsByGenreId(genreid, start, limit, callback);
    }

    public void songsOfChannel(int channelId, int bitRate, Callback callback) {
        SongsGateway songsGateway = new SongsGateway(this, apiGateway);
        songsGateway.querySongsByChannelId(Constants.songType, channelId, bitRate, callback);
    }

    public Context getContext() {
        return context;
    }

    public SharedPreferences getDoubanSharedPreferences() {
        return sharedPreferences;
    }

    public void clear() {
        this.songs = null;
        this.channels = null;
        System.gc();
    }
}
