package com.zzxhdzj.douban.api.channels.fixed;

import android.content.Context;
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
public class StaticChannelRequest extends AuthApiRequest{

    public StaticChannelRequest(int start, int limit, String channelsUrl,Context context) {
        super(context);
        super.appendParameter("start",start+"")
                .appendParameter("limit",limit+"")
        .setBaseUrl(channelsUrl);
    }

    @Override
    public TextApiResponse createResponse(int statusCode,  Map<String, List<String>> headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
