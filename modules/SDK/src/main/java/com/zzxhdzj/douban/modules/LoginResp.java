package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/24/13
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginResp extends Resp{
    @SerializedName("user_info")
    public UserInfo userInfo;
    @SerializedName("err_no")
    public int errNo;

}
