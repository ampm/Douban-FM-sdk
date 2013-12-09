package com.zzxhdzj.douban.api;

import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.Resp;
import com.zzxhdzj.douban.modules.RespStatusCode;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.ApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 12:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseGateway {
    protected final ApiGateway apiGateway;
    protected final Douban douban;
    protected RespType respType;
    public ApiResponse failureResponse;
    public Boolean onCompleteWasCalled;

    public BaseGateway(Douban douban, ApiGateway apiGateway) {
        this.douban = douban;
        this.apiGateway = apiGateway;
    }
    protected boolean isRespOk(Resp resp){
        if(respType!=null){
            if(respType.equals(RespType.R)){
                return resp.r== RespStatusCode.R_TYPE_OK.code;
            } else {
                return resp.status==RespStatusCode.STATUS_TYPE_OK.status;
            }
        }
        return true;
    }
}
