package com.zzxhdzj.app.play;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.zzxhdzj.app.DouCallback;
import com.zzxhdzj.douban.ChannelConstantIds;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.modules.song.Song;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class PlayDelegate {
    private static final int WARNING_SIZE = 5;
    private LinkedList<Song> cachedSongsList;
    public PlayFragment.SongQueueListener songQueueListener;
    private boolean isPLAYING;
    private PlayFragment playFragment;
    private MediaPlayer mp;
    private Douban douban;
    private Song currentPlayingSong;
    private DateTime startInClassScope;

    public PlayDelegate(PlayFragment playFragment) {
        this.playFragment = playFragment;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            playFragment.mSongItem.bindView(currentPlayingSong);
        }
    };
    public void play() {
        isPLAYING = true;
        new Thread() {
            @Override
            public void run() {
                if (cachedSongsList == null || cachedSongsList.size() == 0) {
                    feedMeNewSongsOrReport(ReportType.NEXT_QUEUE, currentPlayingSong != null ? currentPlayingSong.sid : "",
                            startInClassScope != null ? new Interval(startInClassScope, new DateTime()).toDuration().toPeriod().getSeconds() : 0
                            , ChannelConstantIds.PRIVATE_CHANNEL, BitRate.HIGH);
                    isPLAYING = false;
                    return;
                }
                currentPlayingSong = cachedSongsList.remove();
                if (cachedSongsList.size() < WARNING_SIZE) {
                    feedMeNewSongsOrReport(ReportType.NEXT_QUEUE, currentPlayingSong != null ? currentPlayingSong.sid : "",
                            startInClassScope != null ? new Interval(startInClassScope, new DateTime()).toDuration().toPeriod().getSeconds() : 0
                            , ChannelConstantIds.PRIVATE_CHANNEL, BitRate.HIGH);
                }
                handler.sendEmptyMessage(1);
                mp = new MediaPlayer();
                final DateTime start = new DateTime();
                startInClassScope = start;
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //send end report
                        int seconds = new Interval(start, new DateTime()).toDuration().toPeriod().getSeconds();
                        sendReport(ReportType.END, currentPlayingSong.sid, seconds, ChannelConstantIds.PRIVATE_CHANNEL, BitRate.HIGH);
                        mp.release();
                        isPLAYING = false;
                        play();
                    }
                });

                try {
                    mp.setDataSource(currentPlayingSong.url);
                    try {
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.start();
                } catch (IOException e) {
                }
            }
        }.start();

    }

    private void sendReport(ReportType reportType,String songId,int playTime,int channelId,BitRate bitRate) {
        feedMeNewSongsOrReport(reportType, songId,
                playTime
                , channelId, bitRate);
    }

    public void skipAndPlayNext(){
        //send skip report
        int seconds = new Interval(startInClassScope, new DateTime()).toDuration().toPeriod().getSeconds();
        stopPlaying();
        sendReport(ReportType.SKIP, currentPlayingSong.sid, seconds, ChannelConstantIds.PRIVATE_CHANNEL, BitRate.HIGH);
    }
    public void stopPlaying() {
        try {
            mp.release();
            mp = null;
        }catch (Exception e){

        }

        isPLAYING = false;
    }
    private void feedMeNewSongsOrReport(ReportType reportType, String songId, int playTime, int currentChannel, BitRate bitRate) {
        songQueueListener.songListNearlyEmptyOrNeedReport(reportType,songId,playTime,currentChannel,bitRate
                ,new DouCallback(douban) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess() {
                cachedSongsList = douban.songs;
                if(!isPLAYING)play();
            }

            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(Douban.app, douban.mApiRespErrorCode.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });

    }

    public void setDouban(Douban douban) {
        this.douban = douban;
    }
}
