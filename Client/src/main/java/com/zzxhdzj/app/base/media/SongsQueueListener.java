package com.zzxhdzj.app.base.media;


import com.zzxhdzj.douban.ReportType;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/5/14
 * To change this template use File | Settings | File Templates.
 */
public interface SongsQueueListener {
    public void requireNewSongs(ReportType reportType, String songId, int playTime);
}
