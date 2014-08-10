package com.zzxhdzj.douban.api.songs;

import com.google.gson.Gson;
import com.zzxhdzj.douban.ChannelConstantIds;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.modules.song.SongResp;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongsGateway extends BaseApiGateway {

    public static final Gson GSON = new Gson();

    public SongsGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway,RespType.R);
    }

    public void querySongsByChannelId(ReportType songType, String currentSongId, int playTime, int channelId, BitRate bitRate, Callback callback) {
        if(channelId== ChannelConstantIds.PRIVATE_CHANNEL
                ||channelId== ChannelConstantIds.FAV){
            this.isAuthRequire=true;
        }
        apiGateway.makeRequest(new SongsRequest(channelId, bitRate, songType,currentSongId, playTime), new SongApiResponseCallback(callback, this, douban));
    }

    private class SongApiResponseCallback extends CommonTextApiResponseCallback<Douban> {

        private SongResp songResp;

        public SongApiResponseCallback(Callback callback, BaseApiGateway songGateway, Douban douban) {
            super(callback, songGateway, douban);
        }


        @Override
        public void _extractRespData(TextApiResponse response) {
            songResp = GSON.fromJson(response.getResp(), SongResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(songResp)) {
                Douban.songs = songResp.songs;
                return true;
            } else {
                detectAuthError(songResp);
                return false;
            }
        }
    }

}
