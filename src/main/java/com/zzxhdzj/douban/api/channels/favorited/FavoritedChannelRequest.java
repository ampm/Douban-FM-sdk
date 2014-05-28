package com.zzxhdzj.douban.api.channels.favorited;

import android.content.Context;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class FavoritedChannelRequest extends AuthApiRequest<TextApiResponse> {

    protected FavoritedChannelRequest(Context context) {
        super(context);
        super.setBaseUrl(Constants.FAV_CHANNELS);
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
