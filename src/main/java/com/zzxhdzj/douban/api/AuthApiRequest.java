package com.zzxhdzj.douban.api;

import android.content.Context;
import com.zzxhdzj.http.ApiRequest;
import com.zzxhdzj.http.ApiResponse;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 12/11/13
 * Time: 10:23 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AuthApiRequest<T extends ApiResponse> extends ApiRequest {
    protected Context context;

    protected AuthApiRequest(Context context) {
        this.context = context;
    }

//    @Override
//    public Map<String, String> getHeaders() {
//        Map<String,String> headers = super.getHeaders();
//        headers.put("Cookie", Douban.getCookie(context));
//        headers.put("Host", "douban.fm");
//        headers.put("User-Agent", Constants.USER_AGENT);
//        return headers;
//    }
}
