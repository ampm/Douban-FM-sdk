package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChannelRespRoot extends RespRoot {
    @SerializedName("data")
    public ChannlesDatas channlesDatas;
    @SerializedName("song")
    public ArrayList<Song> songs;
}
