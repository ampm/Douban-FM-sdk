package com.zzxhdzj.douban.api.channels.recomment;

import android.content.Context;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;
import org.apache.http.Header;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRecommendChannelRequest extends AuthApiRequest<TextApiResponse> {
    private String userId;
    private final String loginChlsUrl;

    public LoginRecommendChannelRequest(String userId, String loginChlsUrl, Context context) {
        super(context);
        this.userId = userId;
        this.loginChlsUrl = loginChlsUrl;
    }

    public String getUrlString() {
        return loginChlsUrl + "?uk=" + userId;
    }

    @Override
    public TextApiResponse createResponse(int statusCode, Header[] headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
