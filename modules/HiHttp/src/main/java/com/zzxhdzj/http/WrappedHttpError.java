package com.zzxhdzj.http;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/4/14
 * To change this template use File | Settings | File Templates.
 */
public enum  WrappedHttpError {
    CONSUME_ERROR(-2,"parse resp error"),
    REQUEST_ERROR(-1,"req send failed");
    private final int code;
    private final String desc;

    WrappedHttpError(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
