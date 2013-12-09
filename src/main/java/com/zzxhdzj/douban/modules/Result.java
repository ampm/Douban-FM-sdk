package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Result {
    @SerializedName("fav_chls")
    public ArrayList<Channel> favoriteChannels;
    @SerializedName("rec_chls")
    public ArrayList<Channel> recommentChannels;
    public String cover;
    public int id;
    public String intro;
    public String name;
}
