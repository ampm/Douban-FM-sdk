package com.zzxhdzj.douban;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/27/13
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */

public enum ApiInternalError {
    INTERNAL_ERROR("-4", "内部错误:处理失败"),
    CALLER_ERROR("-3", "调用错误:调用出错"),

    UNKNOWN_ERROR("-5", "未知错误"),
    //请求失败
    PARSE_ERROR("-2", "解析服务器的返回时发生错误"),
    AUTH_ERROR("-6", "需要登录"),
    NETWORK_ERROR("-10", "网络异常或服务器无响应"),
    NETWORK_DNS_ERROR("-11", "网络异常:域名无法解析"),
    SERVER_503_ERROR("503", "服务器异常(服务器开小差儿了)"),
    SERVER_404_ERROR("404", "找不到请求地址"),
    SERVER_403_ERROR("403", "访问被拒绝");
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

    public static ApiInternalError getByCode(String code) {
        for(ApiInternalError e : values()) {
            if(e.code.equals(code)) return e;
        }
        return null;
    }
}
