package com.zzxhdzj.douban.api.auth;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.ApiRespErrorCode;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.LoginResp;
import com.zzxhdzj.http.*;
import com.zzxhdzj.http.util.HiUtil;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationGateway extends BaseApiGateway {

    public AuthenticationGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
        respType = RespType.R;
    }

    public void signOut() {
    }

    public void signIn(LoginParams login, Callback responseCallback) {
        apiGateway.makeRequest(new AuthenticationRequest(login),
                new AuthenticationApiResponseCallback(responseCallback));
    }

    class AuthenticationApiResponseCallback implements ApiResponseCallbacks<TextApiResponse> {
        private final Callback callback;

        public AuthenticationApiResponseCallback(Callback responseCallback) {
            this.callback = responseCallback;
        }

        @Override
        public void onSuccess(TextApiResponse response) throws IOException {
            Gson gson = new Gson();
            LoginResp loginResp = gson.fromJson(response.getResp(), LoginResp.class);
            if (isRespOk(loginResp)) {
                Header[] headers = response.getHeaders();
                if (headers != null) {
                    for (int i = 0; i < headers.length; i++) {
                        Header header = headers[i];

                        if (header.getName().equals(HttpHeaders.SET_COOKIE)) {
                            String token = header.getValue();
                            String usefulCookie = collectUsefullCookie(token);
                            douban.getDoubanSharedPreferences().edit().putString(Constants.COOKIE, usefulCookie).commit();
                            break;
                        }
                    }
                }
                try {
                    douban.userInfo = loginResp.userInfo;
                    callback.onSuccess();
                } catch (Exception onSuccessExp) {
                    douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.CALLER_ERROR_ON_SUCCESS);
                    onFailure(response);
                }
            } else {
                douban.apiRespErrorCode = new ApiRespErrorCode(loginResp.errNo + "", loginResp.errMsg);
                onFailure(response);
            }
        }

        @Override
        public void onFailure(ApiResponse response) {
            failureResponse = response;
            if (douban.apiRespErrorCode == null) {
                douban.apiRespErrorCode = new ApiRespErrorCode(ApiInternalError.INTERNAL_ERROR);
            }
            callback.onFailure();
        }

        @Override
        public void onComplete() {
            onCompleteWasCalled = true;
            douban.clear();
        }
    }

    private String collectUsefullCookie(String token) {
        List<NameValuePair> nameValuePairList = HiUtil.covertCookieToNameValuePairs(token);
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<NameValuePair> iterator = nameValuePairList.iterator(); iterator.hasNext(); ) {
            NameValuePair next = iterator.next();
            if(next.getName().equals("bid")||next.getName().equals("ck")||next.getName().equals("dbcl2")){
                stringBuilder.append(next.getName()).append("=").append(next.getValue()).append(";");
            }
        }
        return stringBuilder.toString();
    }
}
