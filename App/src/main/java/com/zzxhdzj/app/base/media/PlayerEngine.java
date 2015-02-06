package com.zzxhdzj.app.base.media;

import com.zzxhdzj.douban.modules.song.Song;

import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/5/14
 * To change this template use File | Settings | File Templates.
 */
public interface PlayerEngine {
    public void loadSongs(LinkedList<Song> songLinkedList);
    public void play();

    public void pause();
    public void next();

    public void skip();

    public void fav();
    public void unfav();

    public void ban();
    public void stop();

    PlayerEngine addPlayerEngineListener(PlayerEngineListener playerEngineListener);
    void setServicePlayerEngineListener(PlayerEngineListener servicePlayerEngineListener);
    void setSongQueueListener(SongsQueueListener songsQueueListener);
}
