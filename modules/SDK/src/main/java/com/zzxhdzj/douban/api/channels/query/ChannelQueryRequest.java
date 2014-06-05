package com.zzxhdzj.douban.api.channels.query;

import android.content.Context;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.TextApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelQueryRequest extends AuthApiRequest {
    protected ChannelQueryRequest(Context context) {
        super(context);
    }
    public static ApiRequest<TextApiResponse> getFavQueryRequest(Context context, String url) {
        return new ChannelQueryRequest(context).setBaseUrl(url);
    }

    public static ApiRequest<TextApiResponse> getHotAndTrendingQueryRequest(int start, int limit, String url, Context context) {
        return new ChannelQueryRequest(context).appendParameter("start", start + "")
                .appendParameter("limit", limit + "")
                .setBaseUrl(url);
    }

    public static ApiRequest<TextApiResponse> getQueryByGenreRequest(int start, int limit, int genreId, String genreChannelUrl, Context context) {
        return new ChannelQueryRequest(context).appendParameter("start", start + "")
                .appendParameter("limit",limit+"")
                .appendParameter("genreId",genreId+"")
                .setBaseUrl(genreChannelUrl);
    }
    public static ApiRequest<TextApiResponse> getLoginRecommendChannelRequest(String userId, String loginChlsUrl, Context context) {
            return new ChannelQueryRequest(context)
                    .appendParameter("uk",userId).setBaseUrl(loginChlsUrl);
    }
    public static ApiRequest<TextApiResponse> getRecommendChannelRequest(ArrayList<Integer> channelIds, String recChlsUrl, Context context) {
        return new ChannelQueryRequest(context)
                .appendParameter("orecs",getOrecsParams(channelIds))
                .setBaseUrl(recChlsUrl);
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Map<String, List<String>> headers) {
        return new TextApiResponse(statusCode,headers);
    }
    public static String getOrecsParams(ArrayList<Integer> channelIds) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < channelIds.size(); i++) {
            Integer index = channelIds.get(i);
            if (i == (channelIds.size() - 1)) {
                stringBuilder.append(index);
            } else stringBuilder.append(index).append("|");
        }
        return stringBuilder.toString();
    }
}
