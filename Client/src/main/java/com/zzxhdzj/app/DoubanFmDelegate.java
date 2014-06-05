package com.zzxhdzj.app;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import com.zzxhdzj.app.login.LoginFragment;
import com.zzxhdzj.app.play.PlayFragment;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.api.channels.local.ChannelHelper;
import com.zzxhdzj.douban.db.tables.ChannelTable;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.http.Callback;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/5/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFmDelegate implements LoginFragment.LoginListener, PlayFragment.SongQueueListener {
    private DoubanFm doubanFm;
    private Douban douban;
    private static final int QUERY_CHANNEL = 1;
    private static final String KEY_LAST_CHLS_QUERY_TIME = "KEY_LAST_CHLS_QUERY_TIME";

    public DoubanFmDelegate(DoubanFm doubanFm) {
        this.doubanFm = doubanFm;
        this.douban = new Douban(doubanFm);
    }

    public void prepareSongs() {
        if (douban.isLogged()) {
            doubanFm.showPlayFragment(this);
            doubanFm.showLoggedItems(douban.getUserInfo());
        } else {
            doubanFm.showLoginFragment();
        }
    }

    public Douban getDouban() {
        return douban;
    }

    public void queryBasicChannelInfo() {
        long lastChlsUpdateTime = Douban.sharedPreferences.getLong(KEY_LAST_CHLS_QUERY_TIME, 0);
        if (isRefeshChannelsOverdue(new DateTime().withMillis(lastChlsUpdateTime))) {
            new ChannelHelper(doubanFm).queryChannels(new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
                    if (loaderId == QUERY_CHANNEL) {
                        return new CursorLoader(doubanFm, ChannelTable.CONTENT_URI,
                                Channel.RECEIPT_PROJECTION, null, null, null);
                    }
                    return null;
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    if (loader.getId() == QUERY_CHANNEL) {
                        fetchChannelsInfo(data);
                    }
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {

                }


            });
        }
    }

    private void fetchChannelsInfo(Cursor data) {
        while (data.moveToNext()) {
            int channelId = data.getInt(Channel.CHANNEL_ID_INDEX);
            douban.queryChannelInfo();
        }
    }

    public boolean isRefeshChannelsOverdue(DateTime lastRequestTime) {
        Period rentalPeriod = new Period().withDays(1);
        return lastRequestTime.plus(rentalPeriod).isBeforeNow();
    }

    @Override
    public void onLogin() {
        doubanFm.showPlayFragment(this);
        doubanFm.showLoggedItems(douban.getUserInfo());
    }


    @Override
    public void songListNearlyEmpty(Callback callback) {
        douban.songsOfPrivateChannels(BitRate.HIGH, callback);
    }

}
