package com.zzxhdzj.douban.modules;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 1:33 AM
 * To change this template use File | Settings | File Templates.
 */
public enum RespStatusCode {
    R_TYPE_OK("r",0),R_TYPE_FAIL("r",1),
    STATUS_TYPE_OK("status",true),STATUS_TYPE_FAIL("status",false),;
    public boolean status;
    public int code;
    public String key;

    RespStatusCode(String key, boolean status) {
        this.key = key;
        this.status = status;
    }

    RespStatusCode(String key, int code) {
        this.key = key;
        this.code = code;
    }

}
