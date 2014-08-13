package com.zzxhdzj.app.base.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.zzxhdzj.app.DouCallback;
import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.app.base.media.PlayerEngine;
import com.zzxhdzj.app.base.media.PlayerEngineImpl;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.base.media.SongsQueueListener;
import com.zzxhdzj.app.base.utils.TimeUtil;
import com.zzxhdzj.app.home.activity.DoubanFm;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.modules.song.Song;
import com.zzxhdzj.http.Callback;

import java.text.DateFormat;
import java.util.Date;
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
    public static final int SHOW_REQUEST_CODE = 100;
    private static final int QUIT_REQUEST_CODE = 101;
    public static final String ACTION_CLOSE_APP = "action_close_app";

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
            displayNotification(DoubanFmApp.getInstance().getCurrentPlayingSong());
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
            mPlayerEngine.loadSongs(Douban.songs);
            //FIXME:log the fav/unfav failed item to db
            mPlayerEngine.play();
        }

        @Override
        public void onFailure() {
            super.onFailure();
            Toast.makeText(DoubanFmApp.getInstance(),douban.mApiRespErrorCode.toString(),Toast.LENGTH_LONG).show();
        }

    };


    private SongsQueueListener mSongQueueListener = new SongsQueueListener() {
        @Override
        public void requireNewSongs(ReportType reportType, String songId, int playTime) {
            //FIXME:the forth param should read from the settings prefs.
            PlayerService.this.reportType = reportType;
            douban.songsOfChannel(reportType, songId, playTime, BitRate.HIGH, DoubanFmApp.getInstance().getCurrentChannelId(),callback);
        }
    };
    private RemoteViews contentView;
    private static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.i(DoubanFmApp.TAG, "Player Service onCreate");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_CLOSE_APP);
        this.registerReceiver(broadcastReceiver, filter);
        // All necessary Application <-> Service pre-setup goes in here
        mPlayerEngine = new PlayerEngineImpl();
        mPlayerEngine.addPlayerEngineListener(mLocalEngineListener);
        mPlayerEngine.setServicePlayerEngineListener(mLocalEngineListener);
        mPlayerEngine.setSongQueueListener(mSongQueueListener);
        mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                Log.e(DoubanFmApp.TAG, "onCallStateChanged");
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
        mWifiLock = mWifiManager.createWifiLock(DoubanFmApp.TAG);
        mWifiLock.setReferenceCounted(false);
        DoubanFmApp.getInstance().setConcretePlayerEngine(mPlayerEngine);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_STICKY;
        }

        String action = intent.getAction();
        Log.i(DoubanFmApp.TAG, "Player Service onStart - " + action);
        if (action.equals(ACTION_BIND_LISTENER)) {
            List<PlayerEngineListener> playerEngineListeners = DoubanFmApp.getInstance().getPlayerEngineListeners();
            if (playerEngineListeners != null) {
                for (PlayerEngineListener listener:playerEngineListeners){
                    mPlayerEngine.addPlayerEngineListener(listener);
                }
            }
            return START_STICKY;
        }

        if (action.equals(ACTION_BAN)) {
            mPlayerEngine.ban();
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent showPlayer = PendingIntent.getActivity(DoubanFmApp.getInstance(), SHOW_REQUEST_CODE,
                intent,PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification noti = new Notification.Builder(this)
                .setContentIntent(showPlayer)
                .setSmallIcon(R.drawable.icon)
                .build();
        final NotificationManager mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        contentView = new RemoteViews(getPackageName(), R.layout.notification);
        contentView.setTextViewText(R.id.noti_song_name_tv, song.title);
        contentView.setTextViewText(R.id.noti_song_artist_tv, song.artist);
        Intent notificationIntent = new Intent(ACTION_CLOSE_APP);
        PendingIntent quitPlayer = PendingIntent.getBroadcast(
                DoubanFmApp.getInstance(), QUIT_REQUEST_CODE,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        contentView.setOnClickPendingIntent(R.id.noti_quit, quitPlayer);
        ImageLoader.getInstance().loadImage(song.picture, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                contentView.setImageViewBitmap(R.id.noti_cover, loadedImage);
                mManager.notify(PLAYING_NOTIFY_ID,noti);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        noti.contentView = contentView;
        noti.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(PLAYING_NOTIFY_ID, noti);
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase(ACTION_CLOSE_APP)){
                stopSelf();
                System.exit(0);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);
    }
}
