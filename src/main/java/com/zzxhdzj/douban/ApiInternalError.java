package com.zzxhdzj.douban;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public enum ApiInternalError {
    INTERNAL_ERROR("500", "内部错误:处理豆瓣服务返回失败"),
    CALLER_ERROR_ON_SUCCESS("-1", "调用错误:调用方ON_SUCCESS出错"),
    NETWORK_ERROR("-2", "网络异常，请确认连接成功后再尝试");
    private String code;
    private String msg;


    private ApiInternalError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
