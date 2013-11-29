package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/29/13
 * Time: 12:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class Song {
    public String aid;
    public String album;
    @SerializedName("albumtitle")
    public String albumTitle;
    public String artist;
    public String company;
    public String kbps;
    @SerializedName("length")
    public String lengthSeconds;
    public boolean like;
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
    String url;
}
