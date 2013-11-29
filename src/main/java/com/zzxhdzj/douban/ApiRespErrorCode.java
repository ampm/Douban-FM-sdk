package com.zzxhdzj.douban;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/20/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiRespErrorCode {
    private int code=500;
    private String msg="程序内部错误";

    public ApiRespErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiRespErrorCode() {
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "[code="+code+",msg="+msg+"]";    //To change body of overridden methods use File | Settings | File Templates.
    }
}
