package com.zzxhdzj.douban.api.channels.favorited;

import android.content.Context;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class FavoredChannelRequest extends AuthApiRequest {

    protected FavoredChannelRequest(Context context) {
        super(context);
        super.setBaseUrl(Constants.FAV_CHANNELS);
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Map<String, List<String>> headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
