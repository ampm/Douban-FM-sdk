package com.zzxhdzj.app;

import android.app.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zzxhdzj.app.base.media.PlayerEngine;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.song.Song;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanApplication extends Application {
    public static final String TAG = "DoubanApplication";
    public static final int WARNING_SIZE = 3;
    public static boolean isPlaying = true;
    private LinkedList<Song> mSongsList;
    private int currentChannelId;
    private Song mCurrentPlayingSong;
    private static DoubanApplication instance;
    private PlayerEngine mServicePlayerEngine;
    public Song getCurrentPlayingSong() {
        return mCurrentPlayingSong;
    }
    public PlayerEngineListener uiPlayerEngineListener;

    public PlayerEngineListener getUiPlayerEngineListener() {
        return uiPlayerEngineListener;
    }

    public void setUiPlayerEngineListener(PlayerEngineListener uiPlayerEngineListener) {
        this.uiPlayerEngineListener = uiPlayerEngineListener;
    }

    public void setCurrentPlayingSong(Song mCurrentPlayingSong) {
        this.mCurrentPlayingSong = mCurrentPlayingSong;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initUniversalImageDownloader();
        Douban.init(getApplicationContext());
        instance = this;
    }

    public static DoubanApplication getInstance() {
        return instance;
    }

    public int getCurrentChannelId() {
        return currentChannelId;
    }

    public void setCurrentChannelId(int currentChannelId) {
        this.currentChannelId = currentChannelId;
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

    public void setConcretePlayerEngine(PlayerEngine mPlayerEngine) {
        mServicePlayerEngine = mPlayerEngine;
    }

    public PlayerEngine getServicePlayerEngine() {
        return mServicePlayerEngine;
    }

}
