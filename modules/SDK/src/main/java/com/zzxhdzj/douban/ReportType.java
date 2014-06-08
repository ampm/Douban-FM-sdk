package com.zzxhdzj.douban;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/8/14
 * To change this template use File | Settings | File Templates.
 */
public enum ReportType {
    NEXT_QUEUE("n"),SKIP("s"),END("e");
    private String code;

    ReportType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
