package com.zzxhdzj.douban;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/8/14
 * To change this template use File | Settings | File Templates.
 */
public enum ReportType {
    NEXT_QUEUE("n",false),SKIP("s",true),END("e",false),
    BAN("b",true),FAV("r",false),UN_FAV("u",false),NULL("n",false);
    private String code;
    private boolean cutCurrentPlaying;

    /**
     *
     * @param code 操作类型
     * @param cutCurrentPlaying 是否对当前播放喊卡
     */
    ReportType(String code,boolean cutCurrentPlaying) {
        this.code = code;
        this.cutCurrentPlaying = cutCurrentPlaying;
    }

    public boolean isCutCurrentPlaying() {
        return cutCurrentPlaying;
    }

    @Override
    public String toString() {
        return code;
    }
}
