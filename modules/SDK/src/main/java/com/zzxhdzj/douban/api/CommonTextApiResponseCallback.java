package com.zzxhdzj.douban.api;

import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.douban.modules.Resp;
import com.zzxhdzj.http.ApiResponseCallbacks;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;


/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 2/25/14
 * Time: 12:08 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CommonTextApiResponseCallback<T extends ApiInstance> implements ApiResponseCallbacks<TextApiResponse> {
    protected final Callback bizCallback;
    protected final BaseApiGateway gateway;
    public ApiInstance apiInstance;
    private String code;
    private String message;

    public CommonTextApiResponseCallback(Callback bizCallback, BaseApiGateway gateway, T apiInstance) {
        this.bizCallback = bizCallback;
        this.gateway = gateway;
        this.apiInstance = apiInstance;
    }


    @Override
    public void onSuccess(TextApiResponse response){
        try {
            _extractRespData(response);
        } catch (Exception e) {
            onProcessFailure(response);
            return;
        }
        if(_handleRespData(response)){
            callOnSuccess(response);
        }else{
            onBizFailure(response);
        }
    }

    protected void callOnSuccess(TextApiResponse response) {
        try {
            bizCallback.onSuccess();
        } catch (Exception onSuccessExp) {
            onCallbackFailure(response);
        }
    }

    public abstract void _extractRespData(TextApiResponse response);
    public abstract boolean _handleRespData(TextApiResponse response);

    @Override
    public void onRequestFailure(TextApiResponse response) {
        ApiInternalError error = ApiInternalError.getByCode(response.getHttpResponseCode() + "");
        if(error!=null){
            //TODO:处理超时
            apiInstance.mApiRespErrorCode = ApiRespErrorCode.createNonBizError(error);
        }else {
            apiInstance.mApiRespErrorCode = ApiRespErrorCode.createNonBizError(response.getHttpResponseCode());
        }
        gateway.failureResponse = response;
        bizCallback.onFailure();
    }

    @Override
    public void onProcessFailure(TextApiResponse response) {
        apiInstance.mApiRespErrorCode = ApiRespErrorCode.createNonBizError(ApiInternalError.INTERNAL_ERROR);
        gateway.failureResponse = response;
        bizCallback.onFailure();
    }

    @Override
    public void onCallbackFailure(TextApiResponse response) {
        apiInstance.mApiRespErrorCode = ApiRespErrorCode.createNonBizError(ApiInternalError.CALLER_ERROR);
        gateway.failureResponse = response;
        bizCallback.onFailure();
    }

    @Override
    public void onStart() {
        bizCallback.onStart();
    }
    @Override
    public void onComplete() {
        gateway.onCompleteWasCalled = true;
        bizCallback.onComplete();
    }

    @Override
    public void onBizFailure(TextApiResponse response) {
        if(apiInstance.mApiRespErrorCode==null){
            apiInstance.mApiRespErrorCode = ApiRespErrorCode.createBizError(code, message);
        }
        bizCallback.onFailure();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    protected void detectAuthError(Resp resp) {
        if (apiInstance.mApiRespErrorCode == null || !resp.getCode(gateway.respType).equals(ApiInternalError.AUTH_ERROR.getCode())) {
            apiInstance.mApiRespErrorCode = ApiRespErrorCode.createBizError(resp.getCode(gateway.respType), resp.getMessage(gateway.respType));
        }
    }
}
