package com.zzxhdzj.douban.api.songs.action;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class UnFavSongRequest extends ApiRequest<TextApiResponse>{
    private final int currentChannelId;
    private final int songId;

    public UnFavSongRequest(int currentChannelId, int songId) {
        this.currentChannelId = currentChannelId;
        this.songId = songId;
    }

    @Override
    public String getUrlString() {
        return Constants.UNFAV_A_SONG_URL+"&channel="+currentChannelId+"&sid="+songId;
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode);
    }
}
