package com.zzxhdzj.douban.api.songs;

import android.content.Context;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.douban.api.base.RandomUtil;
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
//    private final int channelId;
//    private final int bitRate;
//    private final String songType;

    public SongsRequest(int channelId, int bitRate, String songType, Context context) {
        super(context);
        super.appendParameter("channel", channelId + "")
                .appendParameter("kbps", bitRate + "")
                .appendParameter("type", songType + "")
                .appendParameter("from", "mainsite")
                .appendParameter("r", RandomUtil.getRandomString(13))
                .setBaseUrl(Constants.SONGS_URL);
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode, headers);
    }
}
