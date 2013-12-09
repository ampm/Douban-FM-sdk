package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class RecommendChannelRoot extends RespRoot{
    @SerializedName("data")
    public RecommendChannelData recommendChannelData;
}
