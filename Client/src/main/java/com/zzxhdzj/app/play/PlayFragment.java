package com.zzxhdzj.app.play;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.app.DoubanApplication;
import com.zzxhdzj.app.play.view.SongInfoView;
import com.zzxhdzj.douban.ChannelConstantIds;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.modules.song.Song;
import com.zzxhdzj.http.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class PlayFragment extends Fragment implements PlayControlDelegate.ISongChangeListener{
    public static final String TAG = "com.zzxhdzj.app.play.PlayFragment";
    @InjectView(R.id.song_item)
    SongInfoView mSongItem;
    private PlayControlDelegate playControlDelegate;

    public PlayFragment() {
        super();
        this.playControlDelegate = new PlayControlDelegate(this);
		this.playControlDelegate.setSongChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
		if(!DoubanApplication.isPlaying){
			playControlDelegate.play(ChannelConstantIds.PRIVATE_CHANNEL);
		}else {
			mSongItem.bindView(PlayControlDelegate.currentPlayingSong);
		}
    }


    public void setSongQueueListener(SongQueueListener songQueueListener) {
        this.playControlDelegate.songQueueListener = songQueueListener;
    }

    PlayControlDelegate.ISongActionListener songActionListener;

    public void setSongActionListener(PlayControlDelegate.ISongActionListener songActionListener) {
        this.songActionListener = songActionListener;
    }

	@Override public void onPlayStart(Song currentPlayingSong) {
		mSongItem.bindView(currentPlayingSong);
	}

	public interface SongQueueListener {
        void songListNearlyEmptyOrNeedReport(ReportType reportType, String songId, int playTime, int currentChannel, BitRate bitRate, Callback callback);
    }


    public PlayControlDelegate getPlayControlDelegate() {
        return playControlDelegate;
    }


    public void setDouban(Douban douban) {
        playControlDelegate.setDouban(douban);
    }
}
