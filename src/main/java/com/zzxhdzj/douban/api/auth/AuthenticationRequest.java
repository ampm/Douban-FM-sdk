package com.zzxhdzj.douban.api.auth;

import android.content.Context;
import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.http.TextApiResponse;
import com.zzxhdzj.http.util.HiUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HTTP;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/28/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationRequest extends AuthApiRequest {
    private final LoginParams loginParams;

    public AuthenticationRequest(LoginParams loginParams,Context context) {
        super(context);
        this.loginParams = loginParams;
        this.method = HttpPost.METHOD_NAME;
        super.setBaseUrl(Constants.LOGIN_URL);
    }

    @Override
    public HttpEntity getPostEntity() throws Exception {
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(HiUtil.convertMapToNameValuePairs(loginParams.toParamsMap()), HTTP.UTF_8);
        return urlEncodedFormEntity;
    }

    @Override
    public TextApiResponse createResponse(int statusCode,  Map<String, List<String>> headers) {
        return new TextApiResponse(statusCode, headers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationRequest that = (AuthenticationRequest) o;

        if (loginParams != null ? !loginParams.equals(that.loginParams) : that.loginParams != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return loginParams != null ? loginParams.hashCode() : 0;
    }
}
