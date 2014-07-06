package com.zzxhdzj.app.play.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.app.DoubanApplication;
import com.zzxhdzj.app.base.media.ChannelListener;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.base.utils.TimeUtil;
import com.zzxhdzj.app.play.delegate.PlayDelegate;
import com.zzxhdzj.app.play.view.SongInfoView;
import com.zzxhdzj.douban.ChannelConstantIds;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class PlayFragment extends Fragment implements ChannelListener, PlayerEngineListener {
    public static final String TAG = "com.zzxhdzj.app.play.ui.PlayFragment";
    private static final String LAST_OPENED_CHANNEL = "last_opened_channel";
    @InjectView(R.id.song_item)
    SongInfoView mSongItem;
    private int defaultChannel;
    private PlayDelegate playerEngine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerEngine = PlayDelegate.getInstance();
        playerEngine.setPlayerEngineListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_view, container, false);
        ButterKnife.inject(this, view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(DoubanApplication.getInstance().getCurrentPlayingSong()!=null)       {
            mSongItem.bindView(DoubanApplication.getInstance().getCurrentPlayingSong());
        }
        //load last time channel:lastOpenedChannel
        int lastOpenedChannel = Douban.getSharedPreferences().getInt(LAST_OPENED_CHANNEL, -1);
        defaultChannel = ChannelConstantIds.PRIVATE_CHANNEL;
        //is lastOpenedChannel null:currentChannel:PRIVATE_CHANNEL
        //is lastOpenedChannel !null:currentChannel=lastOpenedChannel
        if (lastOpenedChannel > 0) {
            DoubanApplication.getInstance().setCurrentChannelId(lastOpenedChannel);
        } else {
            DoubanApplication.getInstance().setCurrentChannelId(defaultChannel);
        }
        //onChannelChanged
        Channel channel = Channel.queryChannel(DoubanApplication.getInstance().getCurrentChannelId(), getActivity());
        onChannelChanged(channel);
    }

    @Override
    public void onChannelChanged(Channel channel) {
        //if lastOpenedChannel!=currentChannel
        //save lastOpenedChannel to prefs
        if (DoubanApplication.getInstance().getCurrentChannelId() != defaultChannel) {
            Douban.getSharedPreferences()
                    .edit()
                    .putInt(LAST_OPENED_CHANNEL, DoubanApplication.getInstance().getCurrentChannelId());
        }
        //engine play
        if (DoubanApplication.isPlaying) playerEngine.play();
    }

    @Override
    public boolean shouldPlay() {
        return true;
    }

    @Override
    public void onSongChanged() {
        mSongItem.bindView(DoubanApplication.getInstance().getCurrentPlayingSong());
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
        int left = duration - playDuration;
        if (left < 0) {
            left = 0;
        }
        mSongItem.mSongDurationTv.setText(new StringBuilder()
                .append(TimeUtil.periodToMMss(left))
                .append(Douban.app.getResources().getString(R.string.duration_split))
                .append(TimeUtil.periodToMMss(duration)));
    }

    @Override
    public void onBuffering(int percent) {
    }

    public PlayDelegate getPlayerEngine() {
        return playerEngine;
    }
}
