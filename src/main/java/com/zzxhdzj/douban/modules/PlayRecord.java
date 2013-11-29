package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/25/13
 * Time: 12:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class PlayRecord {
    @SerializedName("fav_chls_count")
    public int favChlsCount;
    public int liked;
    public int played;
    public int banned;
}
