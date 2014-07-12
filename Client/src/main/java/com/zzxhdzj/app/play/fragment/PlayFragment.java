package com.zzxhdzj.app.play.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.base.utils.TimeUtil;
import com.zzxhdzj.app.channels.ChannelFragment;
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
public class PlayFragment extends Fragment implements ChannelFragment.ChannelFragmentListener, PlayerEngineListener {
    public static final String TAG = "com.zzxhdzj.app.play.ui.PlayFragment";
    private static final String LAST_OPENED_CHANNEL = "last_opened_channel";
    @InjectView(R.id.song_item)
    SongInfoView mSongItem;
    private PlayDelegate playerEngine;
    private int defaultChannel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playerEngine = PlayDelegate.getInstance();
        playerEngine.setPlayerEngineListener(this);
        DoubanFmApp.getInstance().getChannelFragmentListeners().add(this);
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
        if(DoubanFmApp.getInstance().getCurrentPlayingSong()!=null)       {
            mSongItem.bindView(DoubanFmApp.getInstance().getCurrentPlayingSong());
        }
        //load last time channel:lastOpenedChannel
        int lastOpenedChannel = Douban.getSharedPreferences().getInt(LAST_OPENED_CHANNEL, -1);
        //is lastOpenedChannel null:currentChannel:PRIVATE_CHANNEL
        //is lastOpenedChannel !null:currentChannel=lastOpenedChannel
        if (lastOpenedChannel > 0) {
            DoubanFmApp.getInstance().setCurrentChannelId(lastOpenedChannel);
        } else {
            defaultChannel = ChannelConstantIds.PRIVATE_CHANNEL;
            DoubanFmApp.getInstance().setCurrentChannelId(defaultChannel);
        }
        //engine play
        if (!DoubanFmApp.isPauseByUser) playerEngine.play();
    }


    @Override
    public boolean shouldPlay() {
        return true;
    }

    @Override
    public void onSongChanged() {
        mSongItem.bindView(DoubanFmApp.getInstance().getCurrentPlayingSong());
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

    @Override
    public void onChannelFragmentDestroy() {

    }

    @Override
    public void onChannelSelected(Channel channel) {
        if (channel!=null&&DoubanFmApp.getInstance().getCurrentChannelId()!=channel.id){
            Douban.getSharedPreferences()
                    .edit()
                    .putInt(LAST_OPENED_CHANNEL, channel.id)
                    .commit();
            DoubanFmApp.getInstance().setCurrentChannelId(channel.id);
            if (!DoubanFmApp.isPauseByUser) playerEngine.play();
        }

    }
}
