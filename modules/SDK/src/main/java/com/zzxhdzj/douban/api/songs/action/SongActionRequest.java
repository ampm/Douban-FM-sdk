package com.zzxhdzj.douban.api.songs.action;

import android.content.Context;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 10:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class SongActionRequest extends AuthApiRequest{

    public SongActionRequest(SongActionType songActionType, int currentChannelId, int songId, Context context) {
        super(context);
        super.appendParameter("from", "mainsite")
                .appendParameter("kbps", 64+"")
                .appendParameter("type", songActionType.getCode())
                .appendParameter("channel", currentChannelId + "")
                .appendParameter("sid", songId + "")
                .setBaseUrl(Constants.SONG_ACTION_URL);
    }


    @Override
    public TextApiResponse createResponse(int statusCode, Map<String, List<String>> headers) {
        return null;
    }
}

