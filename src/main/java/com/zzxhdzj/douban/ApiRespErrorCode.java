package com.zzxhdzj.douban;

import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.Resp;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/20/13
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiRespErrorCode {
    private String code;
    private String msg;

    public ApiRespErrorCode(ApiInternalError apiInternalError) {
        this.code = apiInternalError.getCode();
        this.msg = apiInternalError.getMsg();
    }

    public ApiRespErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiRespErrorCode(RespType respType, Resp resp, String msg) {
        if (respType.equals(RespType.R)) {
            this.code = resp.r + "";
        } else this.code = resp.status + "";
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "[code=" + code + ",msg=" + msg + "]";    //To change body of overridden methods use File | Settings | File Templates.
    }
}
