package com.zzxhdzj.douban.modules.channel;

import com.google.gson.annotations.SerializedName;
import com.zzxhdzj.douban.modules.Resp;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/7/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelDetailResp extends Resp{
    @SerializedName("data")
    public ChannelDetailData channelDetailData;
}
