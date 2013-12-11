package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 1:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Resp {
    public boolean status;
    public int r;
    public String msg;
    @SerializedName("err_msg")
    public String errMsg;
}
