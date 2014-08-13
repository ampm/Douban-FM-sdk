package com.zzxhdzj.app.base.media;

import android.media.MediaPlayer;
import android.os.Handler;
import android.text.TextUtils;
import com.andlabs.androidutils.logging.L;
import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.modules.song.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/5/14
 * To change this template use File | Settings | File Templates.
 */
public class PlayerEngineImpl implements PlayerEngine {
    private LinkedList<Song> mSongsList;
    private ArrayList<PlayerEngineListener> playerEngineListeners = new ArrayList<PlayerEngineListener>();
    private PlayerEngineListener servicePlayerEngineListener;

    public ArrayList<PlayerEngineListener> getPlayerEngineListeners() {
        return playerEngineListeners;
    }

    public void setServicePlayerEngineListener(PlayerEngineListener servicePlayerEngineListener) {
        this.servicePlayerEngineListener = servicePlayerEngineListener;
    }

    /**
     * Handler to the context thread
     */
    private Handler mHandler;
    private InternalMediaPlayer mCurrentMediaPlayer;
    private SongsQueueListener songQueueListener;


    /**
     * Simple MediaPlayer extensions, adds reference to the current track
     *
     * @author Lukasz Wisniewski
     */
    private class InternalMediaPlayer extends MediaPlayer {
        public Song currentSong;
        /**
         * Still buffering
         */
        public boolean preparing = false;

        /**
         * Determines if we should play after preparation,
         * e.g. we should not start playing if we are pre-buffering
         * the next track and the old one is still playing
         */
        public boolean playAfterPrepare = false;

        public int currentChannelId;
    }

    /**
     * Runnable periodically querying Media Player
     * about the current position of the track and
     * notifying the listener
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            if (playerEngineListeners != null) {
                // TODO use getCurrentPosition less frequently (usage of currentTimeMillis or uptimeMillis)
                if (mCurrentMediaPlayer != null&&!mCurrentMediaPlayer.preparing) {
                    for (PlayerEngineListener listener : playerEngineListeners) {
                        if (mCurrentMediaPlayer.getDuration() > 24 * 60 * 60 * 1000) return;
                        listener.onSongProgress(mCurrentMediaPlayer.getDuration(), mCurrentMediaPlayer.getCurrentPosition());
                    }
                }
                mHandler.postDelayed(this, 1000);
            }
        }
    };

    public PlayerEngineImpl() {
        this.mHandler = new Handler();
    }

    @Override
    public void loadSongs(LinkedList<Song> songLinkedList) {
        mSongsList = songLinkedList;
    }

    @Override
    public void play() {
        if (servicePlayerEngineListener.shouldPlay()) {
            if (mSongsList != null && mSongsList.size() > 0) {
                if (mSongsList.size() < DoubanFmApp.WARNING_SIZE) {
                    if (songQueueListener != null) {
                        songQueueListener.requireNewSongs(ReportType.NULL, null, 0);
                    }
                }
                if (mCurrentMediaPlayer == null) {//第一次启动
                    mCurrentMediaPlayer = build(mSongsList.remove());
                }
                if (mCurrentMediaPlayer != null && mCurrentMediaPlayer.currentSong != DoubanFmApp.getInstance().getCurrentPlayingSong()) {//换歌了
                    stop();
                    mCurrentMediaPlayer = build(mSongsList.remove());
                }
                if (mCurrentMediaPlayer != null &&DoubanFmApp.getInstance().getCurrentChannelId()!=mCurrentMediaPlayer.currentChannelId) {//换频道了
                    stop();
                    if (songQueueListener != null) {
                        songQueueListener.requireNewSongs(ReportType.NULL, null, 0);
                    }
                }
                // check if there is any player instance, if not, abort further execution
                if (mCurrentMediaPlayer != null) {
                    if (mCurrentMediaPlayer.preparing) {
                        mCurrentMediaPlayer.playAfterPrepare = true;
                    } else {
                        if (!mCurrentMediaPlayer.isPlaying()) {
                            // starting timer
                            mHandler.removeCallbacks(mUpdateTimeTask);
                            mHandler.postDelayed(mUpdateTimeTask, 1000);
                            mCurrentMediaPlayer.start();
                            for (PlayerEngineListener playerEngineListener : playerEngineListeners) {
                                playerEngineListener.onSongChanged();
                            }
                        }
                    }
                }

            } else {
                //query new sons pls
                if (songQueueListener != null) {
                    songQueueListener.requireNewSongs(ReportType.NULL, null, 0);
                }
            }
        }
    }

    private InternalMediaPlayer build(final Song song) {
        final InternalMediaPlayer mediaPlayer = new InternalMediaPlayer();
        if (song != null && !TextUtils.isEmpty(song.url)) {
            try {
                mediaPlayer.setDataSource(song.url);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.preparing = false;
                        if (mediaPlayer.playAfterPrepare) {
                            mediaPlayer.playAfterPrepare = false;
                            play();
                        }
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        L.e("MediaPlayer.OnErrorListener", what, extra);
                        return false;
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        next();
                    }
                });
                mediaPlayer.currentSong = song;
                mediaPlayer.preparing = true;
                mediaPlayer.prepareAsync();
                DoubanFmApp.getInstance().setCurrentPlayingSong(song);
                mediaPlayer.currentChannelId = DoubanFmApp.getInstance().getCurrentChannelId();
                return mediaPlayer;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void next() {
        songQueueListener.requireNewSongs(ReportType.END, mCurrentMediaPlayer.currentSong != null ? mCurrentMediaPlayer.currentSong.sid : "", mCurrentMediaPlayer.getCurrentPosition() / 1000);
        mCurrentMediaPlayer.currentSong = null;
        play();
    }

    @Override
    public void pause() {
        if (mCurrentMediaPlayer != null) {
            // still preparing
            if (mCurrentMediaPlayer.preparing) {
                mCurrentMediaPlayer.playAfterPrepare = false;
                return;
            }
            // check if we play, then pause
            if (mCurrentMediaPlayer.isPlaying()) {
                mCurrentMediaPlayer.pause();
                for (PlayerEngineListener playerEngineListener : playerEngineListeners) {
                    playerEngineListener.onSongPause();
                }
            }
        }
    }

    @Override
    public void skip() {
        songQueueListener.requireNewSongs(ReportType.SKIP,
                getCurrentSongSid(), getCurrentPlayTime());
        if(mCurrentMediaPlayer!=null)mCurrentMediaPlayer.currentSong = null;
        play();
    }

    private int getCurrentPlayTime() {
        return mCurrentMediaPlayer!=null?mCurrentMediaPlayer.getCurrentPosition() / 1000:0;
    }

    private String getCurrentSongSid() {
        String sid;
        if (mCurrentMediaPlayer != null) {
            sid = mCurrentMediaPlayer.currentSong != null ? mCurrentMediaPlayer.currentSong.sid : "";
        } else {
            sid = DoubanFmApp.getInstance().getCurrentPlayingSong().sid;
        }
        return sid;
    }

    @Override
    public void fav() {
        songQueueListener.requireNewSongs(ReportType.FAV, getCurrentSongSid(), getCurrentPlayTime());
    }

    @Override
    public void unfav() {
        songQueueListener.requireNewSongs(ReportType.UN_FAV, getCurrentSongSid(), getCurrentPlayTime());
    }

    @Override
    public void ban() {
        songQueueListener.requireNewSongs(ReportType.BAN, getCurrentSongSid(), getCurrentPlayTime());
        if(mCurrentMediaPlayer!=null)mCurrentMediaPlayer.currentSong = null;
        play();
    }

    @Override
    public void stop() {
        cleanUp();
    }

    @Override
    public PlayerEngine addPlayerEngineListener(PlayerEngineListener playerEngineListener) {
        this.playerEngineListeners.add(playerEngineListener);
        return this;
    }

    @Override
    public void setSongQueueListener(SongsQueueListener songsQueueListener) {
        this.songQueueListener = songsQueueListener;
    }

    /**
     * Stops & destroys media player
     */
    private void cleanUp() {
        // nice clean-up job
        if (mCurrentMediaPlayer != null) {
            try {
                if (mCurrentMediaPlayer.isPlaying()) mCurrentMediaPlayer.stop();
            } catch (Exception e) {
                L.e(e.getMessage());
                // this may happen sometimes
            } finally {
                mCurrentMediaPlayer.release();
                mCurrentMediaPlayer = null;
            }
        }
    }
}
