package com.zzxhdzj.douban.api.base;


import com.zzxhdzj.douban.ApiInternalError;

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
    private ErrorType errorType = ErrorType.BIZ;

    private ApiRespErrorCode(ApiInternalError apiInternalError) {
        this.code = apiInternalError.getCode();
        this.msg = apiInternalError.getMsg();
    }

    public static ApiRespErrorCode createNonBizError(ApiInternalError apiInternalError) {
        ApiRespErrorCode apiRespErrorCode = new ApiRespErrorCode(apiInternalError);
        apiRespErrorCode.errorType = ErrorType.NON_BIZ;
        return apiRespErrorCode;
    }

    public static ApiRespErrorCode createBizError(String code, String message) {
        return new ApiRespErrorCode(code, message);
    }

    public static ApiRespErrorCode createNonBizError(String code, String msg) {
        ApiRespErrorCode apiRespErrorCode = new ApiRespErrorCode(code, msg);
        apiRespErrorCode.errorType = ErrorType.NON_BIZ;
        return apiRespErrorCode;
    }

    private ApiRespErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isBiz() {
        return this.errorType.equals(ErrorType.BIZ);
    }

    @Override
    public String toString() {
        return "[code=" + code + ",msg=" + msg + "]";    //To change body of overridden methods use File | Settings | File Templates.
    }


    public static ApiRespErrorCode createNonBizError(int code) {
        ApiRespErrorCode error = new ApiRespErrorCode(ApiInternalError.NETWORK_ERROR);
        error.errorType = ErrorType.NON_BIZ;
        if (code > 300) {//大于300：服务器返回的http请求错误
            if (code == 404) {
                error.msg = "找不到请求地址";
            } else if (code > 500) {
                error.msg = "服务器内部错误";
            }
        }
        return error;
    }
}

enum ErrorType {
    BIZ, NON_BIZ
}