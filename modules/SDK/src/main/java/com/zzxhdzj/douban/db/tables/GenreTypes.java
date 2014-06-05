package com.zzxhdzj.douban.db.tables;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/5/14
 * To change this template use File | Settings | File Templates.
 */
public enum GenreTypes {
    ROCK(335, "摇滚", "Rock"),
    CLASSIC(326, "古典", "Classic"),
    JAZZ(327, "绝世", "Jazz"),
    COUNTRY(337, "民谣/乡村", "Country"),
    POP(331, "流行", "POP"),
    ELECTRIC(325, "电子", "Electric"),
    ORIGINAL(328, "原声", "Original"),
    EASY(332, "轻音乐", "Easy"),
    RAP(334, "说唱", "Rap"),
    RAGGAE(330, "雷鬼", "Raggae"),
    LATTIIN(329, "拉丁", "Lattin"),
    WORLD(333, "世界音乐", "World"),
    BLUES(324, "布鲁斯", "BLUES"),
    FUNK(336, "放克", "Funk");

    private final int gid;
    private final String desc;
    private final String enDesc;

    GenreTypes(int gid, String desc, String enDesc) {
        this.gid = gid;
        this.desc = desc;
        this.enDesc = enDesc;
    }

    public int getGid() {
        return gid;
    }

    public String getDesc() {
        return desc;
    }

    public String getEnDesc() {
        return enDesc;
    }
}
