package com.zzxhdzj.app.play;

import android.media.MediaPlayer;
import android.widget.Toast;
import com.zzxhdzj.app.DouCallback;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.modules.song.Song;

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
    public PlayDelegate(PlayFragment playFragment) {
        this.playFragment = playFragment;
    }

    public void play() {
        playFragment.getView().post(new Runnable() {
            @Override
            public void run() {
                if (cachedSongsList == null || cachedSongsList.size() == 0) {
                    feedMeNewSongsPlease();
                    return;
                }
                if (isPLAYING) return;
                Song song = cachedSongsList.remove();
                if (cachedSongsList.size() < WARNING_SIZE) {
                    feedMeNewSongsPlease();
                }
                playFragment.mSongItem.bindView(song);
                mp = new MediaPlayer();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        play();
                        isPLAYING = false;
                    }
                });

                try {
                    isPLAYING = true;
                    mp.setDataSource(song.url);
                    try {
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.start();
                } catch (IOException e) {
                }
            }
        });

    }
    public void playNext(){
        stopPlaying();
        play();
    }
    public void stopPlaying() {
        try {
            mp.release();
            mp = null;
        }catch (Exception e){

        }

        isPLAYING = false;
    }
    private void feedMeNewSongsPlease() {
        songQueueListener.songListNearlyEmpty(new DouCallback(douban){
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess() {
                cachedSongsList = douban.songs;
                play();
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
