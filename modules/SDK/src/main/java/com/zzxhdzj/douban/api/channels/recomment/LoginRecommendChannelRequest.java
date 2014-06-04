package com.zzxhdzj.douban.api.channels.recomment;

import android.content.Context;
import com.zzxhdzj.douban.api.AuthApiRequest;
import com.zzxhdzj.http.TextApiResponse;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/9/13
 * Time: 5:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRecommendChannelRequest extends AuthApiRequest {

    public LoginRecommendChannelRequest(String userId, String loginChlsUrl, Context context) {
        super(context);
        super.appendParameter("uk",userId).setBaseUrl(loginChlsUrl);
    }


    @Override
    public TextApiResponse createResponse(int statusCode,Map<String, List<String>> headers) {
        return new TextApiResponse(statusCode,headers);
    }
}
