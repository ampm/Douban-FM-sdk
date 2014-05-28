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

    public RecommendChannelRequest(ArrayList<Integer> channelIds, String recChlsUrl, Context context) {
        super(context);
        super.appendParameter("orecs",getOrecsParams(channelIds))
        .setBaseUrl(recChlsUrl);
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode,headers);
    }

    public String getOrecsParams(ArrayList<Integer> channelIds) {
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
