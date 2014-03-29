package com.zzxhdzj.douban.api.base;

import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.ApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 1/23/14
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class BizGateway {
    protected final ApiGateway apiGateway;
    public RespType respType;
    public Boolean onCompleteWasCalled;
    public ApiResponse failureResponse;

    public BizGateway(ApiGateway apiGateway) {
        this.apiGateway = apiGateway;
    }
}
