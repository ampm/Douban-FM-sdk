package com.zzxhdzj.douban.api.channels.genre;

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
public class GenreChannelRequest extends AuthApiRequest<TextApiResponse> {
    private final int start;
    private final int limit;
    private final String channelsUrl;
    private int genreId;

    public GenreChannelRequest(int start, int limit, int genreId, String channelsUrl,Context context) {
        super(context);
        this.start = start;
        this.limit = limit;
        this.genreId = genreId;
        this.channelsUrl = channelsUrl;
    }

    @Override
    public String getUrlString() {
        return channelsUrl + "?gid="+genreId+"&start=" + start + "&limit=" + limit;
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode);
    }
}
