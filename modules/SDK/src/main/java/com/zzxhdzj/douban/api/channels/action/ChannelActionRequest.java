package com.zzxhdzj.douban.api.channels.action;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelActionRequest extends AuthApiRequest {


    public ChannelActionRequest(ChannelActionType channelActionType, int channelId) {
        super.appendParameter("cid",channelId+"")
        .setBaseUrl(Constants.CHANNEL_ACTION_URL+channelActionType.getKey());
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Map  headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
