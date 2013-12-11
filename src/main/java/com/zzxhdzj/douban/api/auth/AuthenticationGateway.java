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
import org.apache.http.Header;

import java.io.IOException;

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
                            douban.getDoubanSharedPreferences().edit().putString(Constants.COOKIE, token).commit();
                            break;
                        }
                    }
                }
                try {
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
        }
    }
}
