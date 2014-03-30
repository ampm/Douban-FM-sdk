package com.zzxhdzj.douban.api.channels.fixed;

import android.content.Context;
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
public class StaticChannelRequest extends AuthApiRequest<TextApiResponse> {
    private final int start;
    private final int limit;
    public final String channelsUrl;

    public StaticChannelRequest(int start, int limit, String channelsUrl,Context context) {
        super(context);
        this.start = start;
        this.limit = limit;
        this.channelsUrl = channelsUrl;
    }

    @Override
    public String getUrlString() {
        return channelsUrl + "?start=" + start + "&limit=" + limit;
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
