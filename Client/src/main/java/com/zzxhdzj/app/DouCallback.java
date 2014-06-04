package com.zzxhdzj.app;

import android.content.Intent;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.http.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
 * Date: 6/3/14
 * To change this template use File | Settings | File Templates.
 */
public class DouCallback extends Callback {
    private Douban dou;

    public DouCallback(Douban douban) {
        this.dou = douban;
    }

    @Override
    public void onFailure() {
        super.onFailure();
        if(dou.mApiRespErrorCode!=null&&dou.mApiRespErrorCode.getCode().equals(ApiInternalError.AUTH_ERROR.getCode())){
            Intent intent = new Intent();
            intent.setClass(dou.getContext(),DoubanFm.class);
            Douban.app.startActivity(intent);
        }
    }
}
