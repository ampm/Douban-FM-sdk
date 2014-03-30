package com.zzxhdzj.douban.api.songs;

import android.content.Context;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongsRequest extends AuthApiRequest<TextApiResponse> {
    private final int channelId;
    private final int bitRate;
    private final String songType;

    public SongsRequest(int channelId, int bitRate, String songType, Context context) {
        super(context);
        this.channelId = channelId;
        this.bitRate = bitRate;
        this.songType = songType;
    }

    public String getUrlString() {
        return Constants.SONGS_URL + "?channel=" + channelId + "&kbps=" + bitRate + "&type=" + songType;  //To change body of created methods use File | Settings | File Templates.
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
