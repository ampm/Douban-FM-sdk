package com.zzxhdzj.app;

import android.app.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zzxhdzj.douban.Douban;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class Appilication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initUniversalImageDownloader();
        Douban.init(getApplicationContext());
    }

    private void initUniversalImageDownloader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions
                .Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
