package com.zzxhdzj.app.base.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.zzxhdzj.app.DouCallback;
import com.zzxhdzj.app.DoubanApplication;
import com.zzxhdzj.app.base.media.PlayerEngine;
import com.zzxhdzj.app.base.media.PlayerEngineImpl;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.base.media.SongsQueueListener;
import com.zzxhdzj.app.home.activity.DoubanFm;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.modules.song.Song;
import com.zzxhdzj.http.Callback;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/2/14
 * To change this template use File | Settings | File Templates.
 */
public class PlayerService extends Service {
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_SKIP = "skip";
    public static final String ACTION_FAV = "fav";
    public static final String ACTION_BAN = "ban";
    public static final String ACTION_BIND_LISTENER = "bind_listener";
    private static final int PLAYING_NOTIFY_ID = 667667;
    public static final String ACTION_UNFAV = "unfav";

    private WifiManager mWifiManager;
    private WifiManager.WifiLock mWifiLock;
    private PlayerEngine mPlayerEngine;
    private TelephonyManager mTelephonyManager;
    private PhoneStateListener mPhoneStateListener;
    private NotificationManager mNotificationManager = null;
    private PlayerEngineListener mLocalEngineListener = new PlayerEngineListener() {
        @Override
        public boolean shouldPlay() {
            return true;
        }

        @Override
        public void onSongChanged() {
            displayNotification(DoubanApplication.getInstance().getCurrentPlayingSong());
        }

        @Override
        public void onSongStart() {

        }

        @Override
        public void onSongPause() {

        }

        @Override
        public void onFav() {

        }

        @Override
        public void onBan() {

        }

        @Override
        public void onSongProgress(int duration, int playDuration) {

        }

        @Override
        public void onBuffering(int percent) {

        }
    };
    Douban douban = new Douban();
    private ReportType reportType;
    private Callback callback = new DouCallback(douban) {
        @Override
        public void onSuccess() {
            super.onSuccess();
            mPlayerEngine.loadSongs(douban.songs);
            //FIXME:log the fav/unfav failed item to db
            mPlayerEngine.play();
        }
    };


    private SongsQueueListener mSongQueueListener = new SongsQueueListener() {
        @Override
        public void requireNewSongs(ReportType reportType, String songId, int playTime) {
            //FIXME:the forth param should read from the settings prefs.
            PlayerService.this.reportType = reportType;
            douban.songsOfChannel(reportType, songId, playTime, BitRate.HIGH, callback);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(DoubanApplication.TAG, "Player Service onCreate");

        // All necessary Application <-> Service pre-setup goes in here
        mPlayerEngine = new PlayerEngineImpl();
        mPlayerEngine.addPlayerEngineListener(mLocalEngineListener);
        mPlayerEngine.setServicePlayerEngineListener(mLocalEngineListener);
        mPlayerEngine.setSongQueueListener(mSongQueueListener);
        mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                Log.e(DoubanApplication.TAG, "onCallStateChanged");
                if (state == TelephonyManager.CALL_STATE_IDLE) {
                    // resume playback
                } else {
                    if (mPlayerEngine != null) {
                        mPlayerEngine.pause();
                    }
                }
            }

        };
        mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiLock = mWifiManager.createWifiLock(DoubanApplication.TAG);
        mWifiLock.setReferenceCounted(false);
        DoubanApplication.getInstance().setConcretePlayerEngine(mPlayerEngine);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }

        String action = intent.getAction();
        Log.i(DoubanApplication.TAG, "Player Service onStart - " + action);
        if (action.equals(ACTION_BIND_LISTENER)) {
            List<PlayerEngineListener> playerEngineListeners = DoubanApplication.getInstance().getPlayerEngineListeners();
            if (playerEngineListeners != null) {
                for (PlayerEngineListener listener:playerEngineListeners){
                    mPlayerEngine.addPlayerEngineListener(listener);
                }
            }
            return START_STICKY;
        }

        if (action.equals(ACTION_BAN)) {
            stopSelfResult(startId);
            return START_STICKY;
        }

        if (action.equals(ACTION_PLAY)) {
            mPlayerEngine.play();
            return START_STICKY;
        }

        if (action.equals(ACTION_SKIP)) {
            mPlayerEngine.skip();
            return START_STICKY;
        }

        if (action.equals(ACTION_FAV)) {
            mPlayerEngine.fav();
            return START_STICKY;
        }
        if (action.equals(ACTION_UNFAV)) {
            mPlayerEngine.unfav();
            return START_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void displayNotification(Song song) {


        Intent intent = new Intent(this, DoubanFm.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(DoubanApplication.getInstance(), 0,
                intent, 0);
        Notification noti = new Notification.Builder(this)
                .setContentTitle(song.title)
                .setContentText(song.artist)
                .setContentIntent(pIntent)
                .setSmallIcon(R.drawable.icon)
                .setSubText(song.albumTitle+" - "+song.publicTime)
                .build();
        noti.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(PLAYING_NOTIFY_ID, noti);
    }
}
