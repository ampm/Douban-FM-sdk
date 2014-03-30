package com.zzxhdzj.douban.api.auth;

import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;
import com.zzxhdzj.douban.CacheConstant;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.CommonTextApiResponseCallback;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.douban.api.base.BaseApiGateway;
import com.zzxhdzj.http.ApiGateway;
import com.zzxhdzj.http.ApiResponse;
import com.zzxhdzj.http.Callback;
import com.zzxhdzj.http.TextApiResponse;
import com.zzxhdzj.http.util.HiUtil;
import org.apache.http.Header;
import org.apache.http.NameValuePair;

import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 11/26/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenGetCaptchaGateway extends BaseApiGateway {

    public AuthenGetCaptchaGateway(Douban douban, ApiGateway apiGateway) {
        super(douban, apiGateway);
    }

    public void newCaptchaId(Callback callback) {
        apiGateway.makeRequest(new AuthGetCaptchaRequest(douban.getContext()), new AuthenGetCaptchaCallback(callback, this, douban));
    }


    private class AuthenGetCaptchaCallback extends CommonTextApiResponseCallback<Douban> {

        public AuthenGetCaptchaCallback(Callback bizCallback, BaseApiGateway gateway, Douban apiInstance) {
            super(bizCallback, gateway, apiInstance);
        }

        @Override
        public void _extractRespData(TextApiResponse response) {
            Gson gson = new Gson();
            Object obj = gson.fromJson(response.getResp(), Object.class);
            douban.captchaId = obj.toString();
        }

        @Override
        public boolean _handleRespData(TextApiResponse response){
            if (TextUtils.isEmpty(douban.captchaId)) {
                douban.mApiRespErrorCode = ApiRespErrorCode.createBizError("-1", "验证码ID获取失败");
                return false;
            }else {
                douban.captchaImageUrl = Constants.CAPTCHA_URL + "&id=" + douban.captchaId;
                return true;
            }
        }
        @Override
        public void onRequestFailure(ApiResponse response) {
            if(response.getHttpResponseCode()==302){
                saveCookie(response);
                super.onProcessFailure(response);
            }else {
                super.onRequestFailure(response);
            }
        }

    }
    private void saveCookie(ApiResponse response) {
        Header[] headers = response.getHeaders();
        if (headers != null) {
            StringBuilder sb = null;
            for (int i = 0; i < headers.length; i++) {
                Header header = headers[i];

                if (header.getName().equals(HttpHeaders.SET_COOKIE)) {
                    if(sb==null)sb = new StringBuilder();
                    String token = header.getValue();
                    sb.append(filterUselessCookie(token));
                }
            }
            douban.getDoubanSharedPreferences().edit().putString(CacheConstant.COOKIE_KEY, sb.toString()).commit();
        }
    }
    private String filterUselessCookie(String token) {
        List<NameValuePair> nameValuePairList = HiUtil.covertCookieToNameValuePairs(token);
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<NameValuePair> iterator = nameValuePairList.iterator(); iterator.hasNext(); ) {
            NameValuePair next = iterator.next();
            if (next.getName().equals("bid")/* || next.getName().equals("flag") || next.getName().equals("ck") || next.getName().equals("dbcl2")*/) {
                stringBuilder.append(next.getName()).append("=").append(next.getValue()).append(";");
            }
        }
        return stringBuilder.toString();
    }
}
