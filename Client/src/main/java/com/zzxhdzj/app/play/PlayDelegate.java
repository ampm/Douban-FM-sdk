package com.zzxhdzj.app.play;

import android.media.MediaPlayer;
import com.zzxhdzj.app.DouCallback;
import com.zzxhdzj.douban.modules.song.Song;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
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

    public PlayDelegate(PlayFragment playFragment) {
        this.playFragment = playFragment;
    }

    public void play() {
        playFragment.getView().post(new Runnable() {
            @Override
            public void run() {
                if (cachedSongsList == null || cachedSongsList.size() == 0) {
                    refreshSongs();
                    return;
                }
                if (isPLAYING) return;
                Song song = cachedSongsList.remove();
                if (cachedSongsList.size() < WARNING_SIZE) {
                    refreshSongs();
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
        try{
            stopPlaying();
        }catch (Exception e){
        }
        play();
    }
    public void stopPlaying() {
        mp.release();
        mp = null;
        isPLAYING = false;
    }
    private void refreshSongs() {
        songQueueListener.songListNearlyEmpty(new DouCallback(playFragment.douban){
            @Override
            public void onSuccess() {
                super.onSuccess();
                cachedSongsList = playFragment.getDouban().songs;
                play();
            }

        });

    }
}
