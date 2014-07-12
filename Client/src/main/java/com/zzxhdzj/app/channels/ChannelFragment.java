package com.zzxhdzj.app.channels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.channel.Channel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 7/12/14
 * To change this template use File | Settings | File Templates.
 */
public class ChannelFragment extends Fragment {
    @InjectView(R.id.btn_channel_item_region)
    TextView mBtnChannelItemRegion;
    @InjectView(R.id.btn_channel_item_ages)
    TextView mBtnChannelItemAges;
    @InjectView(R.id.btn_channel_item_genre)
    TextView mBtnChannelItemGenre;
    @InjectView(R.id.btn_channel_item_special)
    TextView mBtnChannelItemSpecial;
    @InjectView(R.id.btn_channel_item_genre)
    TextView mBtnQuickGenre;
    @InjectView(R.id.btn_channel_item_artist)
    TextView mBtnChannelItemArtist;
    @InjectView(R.id.btn_channel_item_trending)
    TextView mBtnChannelItemTrending;
    @InjectView(R.id.btn_channel_item_hits)
    TextView mBtnChannelItemHits;
    @InjectView(R.id.btn_channel_item_try)
    TextView mBtnChannelItemTry;
    @InjectView(R.id.btn_search)
    ImageView mBtnSearch;
    private List<ChannelFragmentListener> channelFragmentListeners = DoubanFmApp.getInstance().getChannelFragmentListeners();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels, container, false);
        ButterKnife.inject(this, view);
        return view;
    }
    @OnClick({
            R.id.btn_channel_item_region,
            R.id.btn_channel_item_ages,
            R.id.btn_channel_item_genre,
            R.id.btn_channel_item_special,
            R.id.btn_channel_item_artist,
            R.id.btn_channel_item_trending,
            R.id.btn_channel_item_hits,
            R.id.btn_channel_item_try
    })
    public void registerOnClickListener(View view) {
        switch (view.getId()){
            case R.id.btn_channel_item_region:
                for(ChannelFragmentListener listener:channelFragmentListeners){
                    listener.onChannelSelected(Channel.queryChannel(6, getActivity()));
                }
                FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
                supportFragmentManager.popBackStack();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for(ChannelFragmentListener listener:channelFragmentListeners){
            listener.onChannelFragmentDestroy();
        }
    }

    public interface ChannelFragmentListener{
        void onChannelFragmentDestroy();
        void onChannelSelected(Channel channel);
    }
}
