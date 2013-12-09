package com.zzxhdzj.douban.api.songs.action;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class FavSongRequest extends ApiRequest<TextApiResponse> {
    private final int currentChannelId;
    private final int songId;

    public FavSongRequest(int currentChannelId, int songId) {
        this.currentChannelId = currentChannelId;
        this.songId = songId;
    }

    @Override
    public String getUrlString() {
        return Constants.FAV_A_SONG_URL+"&channel="+currentChannelId+"&sid="+songId;
    }

    @Override
    public TextApiResponse createResponse(int i, Header[] headers) {
        return null;
    }
}
