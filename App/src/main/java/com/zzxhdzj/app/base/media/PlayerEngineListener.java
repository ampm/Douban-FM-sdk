package com.zzxhdzj.app.base.media;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/5/14
 * To change this template use File | Settings | File Templates.
 */
public interface PlayerEngineListener {
    /**
     * By default, the PlayerEngine will play songs wifi-only.
     * If the if user insist to play song over a cellular network after a mention dialog,
     * this shouldPlay return a TRUE answer.
     * @return
     */
    public boolean shouldPlay();
    public void onSongChanged();
    public void onSongStart();
    public void onSongPause();
    public void onFav();
    public void onBan();
    public void onSongProgress(int duration, int playDuration);
    public void onBuffering(int percent);
}
