package com.zzxhdzj.douban.api.songs.action;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 10:21 AM
 * To change this template use File | Settings | File Templates.
 */
public enum  SongActionType {
    FAV("r"),UNFAV("u"),SKIP("s"),BAN("b");
    private final String code;

    SongActionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
