package com.zzxhdzj.douban.api.channels.action;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelActionRequest extends ApiRequest<TextApiResponse>{

    private final ChannelActionType channelActionType;
    private final int channelId;

    public ChannelActionRequest(ChannelActionType channelActionType, int channelId) {
        this.channelActionType = channelActionType;
        this.channelId = channelId;
    }

    @Override
    public String getUrlString() {
        return Constants.CHANNEL_ACTION_URL+channelActionType.getKey()+"?cid="+channelId;
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode);
    }
}
