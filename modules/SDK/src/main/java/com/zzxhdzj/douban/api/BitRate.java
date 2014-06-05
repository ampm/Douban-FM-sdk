package com.zzxhdzj.douban.api;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/2/14
 * To change this template use File | Settings | File Templates.
 */
public enum  BitRate {
    LOW(64),MEDIUM(128),HIGH(192);
    private int bitRate;
    BitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    @Override
    public String toString() {
        return this.bitRate+"";
    }
}
