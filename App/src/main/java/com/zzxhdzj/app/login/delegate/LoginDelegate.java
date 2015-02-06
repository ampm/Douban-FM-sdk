package com.zzxhdzj.app.login.delegate;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.zzxhdzj.douban.ApiInternalError;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.base.ApiRespErrorCode;
import com.zzxhdzj.http.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class LoginDelegate {
    private Context context;
    private DisplayImageOptions options;

    public LoginDelegate(Context activity) {
        this.context = activity;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisc(false)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    public void showCaptcha(final Douban douban, final ImageView captchaImageView, final ProgressBar mLoading) {
        douban.fetchCaptcha(new Callback() {
            @Override
            public void onStart() {
                super.onStart();
                mLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                ImageLoader.getInstance().displayImage(douban.captchaImageUrl, captchaImageView, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        douban.mApiRespErrorCode = ApiRespErrorCode.createNonBizError(ApiInternalError.INTERNAL_ERROR.getCode(), "图片加载失败");
                        onFailure();
                        mLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        mLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        mLoading.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(context, douban.mApiRespErrorCode.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }


}
