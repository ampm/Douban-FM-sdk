package com.zzxhdzj.douban.api.base;

import com.zzxhdzj.http.ApiGateway;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 2/25/14
 * Time: 12:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiInstance {
    public ApiRespErrorCode mApiRespErrorCode;
    protected ApiGateway apiGateway = new ApiGateway();

    public ApiGateway getApiGateway() {
        return apiGateway;
    }
}
