package com.zzxhdzj.douban.api.channels.recomment;

import android.content.Context;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendChannelRequest extends AuthApiRequest<TextApiResponse> {
    private ArrayList<Integer> channelIds;
    private final String recChlsUrl;

    public RecommendChannelRequest(ArrayList<Integer> channelIds, String recChlsUrl, Context context) {
        super(context);
        this.channelIds = channelIds;
        this.recChlsUrl = recChlsUrl;
    }

    public String getUrlString() {
        return recChlsUrl + "?orecs=" + getOrecsParams();
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode);
    }

    public String getOrecsParams() {
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
