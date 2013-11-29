package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/28/13
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Channel {
    public String banner;
    public String cover;
    public Creator creator;
    @SerializedName("hot_songs")
    public String[] hotSongs;
    public int id;
    public String intro;
    public String name;
    @SerializedName("song_num")
    public int songNum;
}
