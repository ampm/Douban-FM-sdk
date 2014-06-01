package com.zzxhdzj.douban.modules.song;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class Song implements Serializable{
    public String aid;
    public String album;
    @SerializedName("albumtitle")
    public String albumTitle;
    public String artist;
    public String company;
    public String kbps;
    @SerializedName("length")
    public String lengthSeconds;
    public String like;//有时候返回是false 有时候返回是1/0
    public String picture;
    @SerializedName("public_time")
    public String publicTime;
    @SerializedName("rating_avg")
    public String ratingAvg;
    public String sha256;
    public String sid;
    public String ssid;
    @SerializedName("subtype")
    public String subType;
    public String title;
    public String url;
}
