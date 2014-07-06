package com.zzxhdzj.douban.api.channels.query;

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
    public static ApiRequest<TextApiResponse> getFavQueryRequest(String url) {
        return new ChannelQueryRequest().setBaseUrl(url);
    }

    public static ApiRequest<TextApiResponse> getHotAndTrendingQueryRequest(int start, int limit, String url) {
        return new ChannelQueryRequest().appendParameter("start", start + "")
                .appendParameter("limit", limit + "")
                .setBaseUrl(url);
    }

    public static ApiRequest<TextApiResponse> getQueryByGenreRequest(int start, int limit, int genreId, String genreChannelUrl) {
        return new ChannelQueryRequest().appendParameter("start", start + "")
                .appendParameter("limit",limit+"")
                .appendParameter("genreId",genreId+"")
                .setBaseUrl(genreChannelUrl);
    }
    public static ApiRequest<TextApiResponse> getLoginRecommendChannelRequest(String userId, String loginChlsUrl) {
            return new ChannelQueryRequest()
                    .appendParameter("uk",userId).setBaseUrl(loginChlsUrl);
    }
    public static ApiRequest<TextApiResponse> getRecommendChannelRequest(ArrayList<Integer> channelIds, String recChlsUrl) {
        return new ChannelQueryRequest()
                .appendParameter("orecs",getOrecsParams(channelIds))
                .setBaseUrl(recChlsUrl);
    }

    public static ApiRequest<TextApiResponse> getASingleQueryRequest(String channelId, String url) {
        return new ChannelQueryRequest().setBaseUrl(url).appendParameter("channel_id",channelId);
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
