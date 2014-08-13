package com.zzxhdzj.douban.modules;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/25/13
 * Time: 12:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo implements Serializable {
    private String ck;
    @SerializedName("play_record")
    public PlayRecord playRecord;
    @SerializedName("is_new_user")
    private int isNewUser;
    public String uid;
    @SerializedName("thid_party_info")
    public String thidPartyInfo;
    public String url;
    @SerializedName("is_dj")
    public String isDj;
    public String id;
    @SerializedName("is_pro")
    public boolean isPro;
    public String name;
}
