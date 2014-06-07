package com.zzxhdzj.app;

import android.database.Cursor;
import com.zzxhdzj.app.login.LoginFragment;
import com.zzxhdzj.app.play.PlayFragment;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.api.channels.local.ChannelHelper;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.http.Callback;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;

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
    private ChannelHelper channelHelper;

    public DoubanFmDelegate(DoubanFm doubanFm) {
        this.doubanFm = doubanFm;
        this.douban = new Douban(doubanFm);
        channelHelper = new ChannelHelper(doubanFm);
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

    public void updateStaticChannelInfo() {
        long lastChlsUpdateTime = Douban.sharedPreferences.getLong(KEY_LAST_CHLS_QUERY_TIME, 0);
        if (isRefreshChannelsOverdue(new DateTime().withMillis(lastChlsUpdateTime))) {
            channelHelper.queryStaticChannels(new ChannelHelper.ChannelHelperListener() {
                @Override
                public void onResult(Cursor data) {
                    Douban.sharedPreferences.edit().putLong(KEY_LAST_CHLS_QUERY_TIME,new DateTime().getMillis());
                    //更新固定频率id
                    fetchChannelsInfo(data);
                }
            });
        }
    }

    public void updateDynamicChannels(){
        //根据我听的比较多的频道推荐频道:mock
        final ArrayList<Integer> channelIds = new ArrayList<Integer>();
        //FIXME:从数据库查点击次数最多的
        channelIds.add(1);
        channelIds.add(3);
        channelIds.add(5);

        final ArrayList<Channel> tempChannels = new ArrayList<Channel>();

        //step3
        final Callback recommendCallback = new Callback() {
            @Override
            public void onSuccess() {
                synchronized (tempChannels){
                    tempChannels.add(douban.recommendChannel);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                persistDynamicChannels(tempChannels);
            }
        };

        //step2
        //前7名
        final Callback trendingCallback = new Callback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                synchronized (tempChannels){
                    tempChannels.addAll(douban.channels);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                douban.recommendChannnels(channelIds, recommendCallback);
            }
        };

        //step1
        Callback hotCallback = new Callback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                synchronized (tempChannels){
                    tempChannels.addAll(douban.channels);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                douban.queryTrendingChannles(1, 9, trendingCallback);
            }
        };

        douban.queryHotChannels(1, 9, hotCallback);
    }
    void persistDynamicChannels(final ArrayList<Channel> tempChannels){
        channelHelper.queryDynamicChannels(new ChannelHelper.ChannelHelperListener() {
            @Override
            public void onResult(final Cursor data) {
                 channelHelper.createOrUpdateNoneStaticChannels(data,tempChannels);
            }
        });
    }

    private void fetchChannelsInfo(Cursor data) {
        //更新数据库中的固定频道信息
        while (data.moveToNext()) {
            String channelId = data.getString(Channel.CHANNEL_ID_INDEX);
            //去网上查询然后更新到数据库
            douban.queryChannelInfo(channelId, new Callback(){
                @Override
                public void onSuccess() {
                    super.onSuccess();
                    channelHelper.update((Channel) douban.singleObject);
                }
            });
        }
    }

    public boolean isRefreshChannelsOverdue(DateTime lastRequestTime) {
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
