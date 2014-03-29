package com.zzxhdzj.douban.api.base;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/20/13
 * Time: 12:51 AM
 * To change this template use File | Settings | File Templates.
 */
public enum BizRespCode {//业务协议自定义的resp code
    OK("resultCode","0");
    private String key;
    private String code;

    BizRespCode(String key, String code) {
        this.key = key;
        this.code = code;
    }

    public String getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }
}
