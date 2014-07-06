package com.zzxhdzj.douban.api.base;

import android.text.TextUtils;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.PrefsConstant;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.Resp;
import com.zzxhdzj.douban.modules.RespStatusCode;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.ApiResponse;
import com.zzxhdzj.http.TextApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/10/13
 * Time: 12:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class BaseApiGateway {
    protected final ApiGateway<TextApiResponse> apiGateway;
    protected final Douban douban;
    public RespType respType;
    protected boolean isAuthRequire;
    public ApiResponse failureResponse;
    public Boolean onCompleteWasCalled;

    public BaseApiGateway(Douban douban, ApiGateway apiGateway,RespType respType) {
        this.douban = douban;
        this.apiGateway = apiGateway;
        this.respType = respType;
    }

    protected boolean isRespOk(Resp resp) {
        boolean isOk = false;
        if (respType != null) {
            if (respType.equals(RespType.R)) {
                isOk = resp.r == RespStatusCode.R_TYPE_OK.code;

            } else {
                isOk = resp.status == RespStatusCode.STATUS_TYPE_OK.status;
            }
        }
        if(!TextUtils.isEmpty(resp.warning)&&(resp.warning.contains("user_is_ananymous")||resp.warning.contains("user_is_anonymous"))){
            //anonymous:豆瓣官方拼写错误，防止他将来纠正过来,user_is_anonymous 也判断一下
            Douban.getSharedPreferences().edit().putBoolean(PrefsConstant.LOGGED, false).commit();
            if(isAuthRequire){
                isOk = false;
                douban.mApiRespErrorCode = ApiRespErrorCode.createNonBizError(ApiInternalError.AUTH_ERROR);
            }
        }
        return isOk;
    }
}
