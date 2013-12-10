package com.zzxhdzj.douban.api.channels.action;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 2:57 PM
 * To change this template use File | Settings | File Templates.
 */
public enum  ChannelActionType {
    FAV_CHANNEL("fav_channel"), UNFAV_CHANNEL("unfav_channel");
    private String key;

    ChannelActionType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
