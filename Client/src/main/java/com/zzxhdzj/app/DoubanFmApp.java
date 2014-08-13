package com.zzxhdzj.app;

import android.app.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zzxhdzj.app.base.media.PlayerEngine;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.song.Song;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFmApp extends Application {
    public static final String TAG = "DoubanApplication";
    public static final int WARNING_SIZE = 1;
    public static boolean isPauseByUser = false;
    private LinkedList<Song> mSongsList;
    private int currentChannelId;
    private Song mCurrentPlayingSong;
    private static DoubanFmApp instance;
    private PlayerEngine mServicePlayerEngine;
    public Song getCurrentPlayingSong() {
        return mCurrentPlayingSong;
    }
    public List<PlayerEngineListener> playerEngineListeners = new ArrayList<PlayerEngineListener>();

    public List<PlayerEngineListener> getPlayerEngineListeners() {
        return playerEngineListeners;
    }

    public List<PlayerEngineListener> addPlayerEngineListener(PlayerEngineListener playerEngineListener) {
        this.playerEngineListeners.add(playerEngineListener);
        return playerEngineListeners;
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

    public static DoubanFmApp getInstance() {
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
