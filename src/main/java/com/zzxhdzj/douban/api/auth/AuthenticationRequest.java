package com.zzxhdzj.douban.api.auth;

import com.zzxhdzj.douban.Constants;
import com.zzxhdzj.douban.modules.LoginParams;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/28/13
 * Time: 12:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationRequest extends ApiRequest<TextApiResponse> {
    private final LoginParams login;

    public AuthenticationRequest(LoginParams login) {
        this.login = login;
    }

    @Override
    public String getUrlString() {
        return Constants.LOGIN_URL;
    }

    @Override
    public HttpEntity getPostEntity() throws Exception {
        return super.getPostEntity();
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode, headers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationRequest that = (AuthenticationRequest) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
