package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginChannelsRoot extends RespRoot {
    @SerializedName("data")
    public LoginChannelsData loginChannelsData;
}
