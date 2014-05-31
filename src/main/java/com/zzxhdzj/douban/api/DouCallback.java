package com.zzxhdzj.douban.api;

import android.content.Context;
import com.zzxhdzj.douban.api.base.ApiInstance;
import com.zzxhdzj.http.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
 * Date: 5/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DouCallback extends Callback{
    private ApiInstance douban;
    private Context context;

    public DouCallback(ApiInstance douban,Context context) {
        this.douban = douban;
        this.context = context;
    }

    @Override
    public void onFailure() {
        super.onFailure();
        if(isCookieStillValid()){
            _onFailure();
        }else {
            onAuthFailure();
        }
    }

    private void onAuthFailure() {

    }

    public void _onFailure() {

    }

    private boolean isCookieStillValid() {
        return false;
    }
}
