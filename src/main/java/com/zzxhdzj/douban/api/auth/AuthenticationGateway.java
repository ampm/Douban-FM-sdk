package com.zzxhdzj.douban.api.auth;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.zzxhdzj.douban.CacheConstant;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BaseApiGateway;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.RespType;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.douban.modules.LoginResp;
import com.zzxhdzj.douban.modules.UserInfo;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;
import com.zzxhdzj.http.util.HiUtil;
import org.afinal.simplecache.ACache;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationGateway<T extends ApiInstance> extends BaseApiGateway {


    public AuthenticationGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway, RespType.R);
    }

    public void signOut() {
    }

    public void signIn(LoginParams login, Callback responseCallback) {
        apiGateway.makeRequest(new AuthenticationRequest(login),
                new AuthenticationApiResponseCallback(responseCallback, this, douban));
    }

    private void saveUserInfo(UserInfo userInfo) {
        ACache aCache = ACache.get(douban.getContext());
        aCache.put("user_info", userInfo);
    }

    private void saveCookie(TextApiResponse response) {
        Header[] headers = response.getHeaders();
        if (headers != null) {
            for (int i = 0; i < headers.length; i++) {
                Header header = headers[i];
                if (header.getName().equals(HttpHeaders.SET_COOKIE)) {
                    String token = header.getValue();
                    String usefulCookie = filterUselessCookie(token);
                    douban.getDoubanSharedPreferences().edit().putString(CacheConstant.COOKIE_KEY, usefulCookie).commit();
                    break;
                }
            }
        }
    }

    private String filterUselessCookie(String token) {
        List<NameValuePair> nameValuePairList = HiUtil.covertCookieToNameValuePairs(token);
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<NameValuePair> iterator = nameValuePairList.iterator(); iterator.hasNext(); ) {
            NameValuePair next = iterator.next();
            if (next.getName().equals("bid") || next.getName().equals("ck") || next.getName().equals("dbcl2")) {
                stringBuilder.append(next.getName()).append("=").append(next.getValue()).append(";");
            }
        }
        return stringBuilder.toString();
    }

    private class AuthenticationApiResponseCallback extends CommonTextApiResponseCallback<Douban> {
        private LoginResp loginResp;
        public AuthenticationApiResponseCallback(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response){
            Gson gson = new Gson();
            loginResp = gson.fromJson(response.getResp(), LoginResp.class);
        }

        @Override
        public boolean _handleRespData(TextApiResponse response) {
            if (isRespOk(loginResp)) {
                saveCookie(response);
                saveUserInfo(loginResp.userInfo);
                return true;
//                callOnSuccess(response);
            } else {
                douban.mApiRespErrorCode = ApiRespErrorCode.createBizError(loginResp.getCode(respType), loginResp.getMessage(respType));
//                onBizFailure(response);
                return false;
            }
        }

        @Override
        public void onComplete() {
            super.onComplete();
            douban.clear();
        }
    }
}
