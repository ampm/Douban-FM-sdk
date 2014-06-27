package com.zzxhdzj.app.play;

import android.media.MediaPlayer;
import android.widget.Toast;
import com.zzxhdzj.app.DouCallback;
import com.zzxhdzj.app.DoubanApplication;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.modules.song.Song;
import com.zzxhdzj.http.asynctask.PoolAsyncTask;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class PlayControlDelegate {
    private static final int WARNING_SIZE = 2;
    private static LinkedList<Song> cachedSongsList;
    public PlayFragment.SongQueueListener songQueueListener;
    private PlayFragment playFragment;
    private MediaPlayer mp;
    private Douban douban;
    private static DateTime startInClassScope;
    public static Song currentPlayingSong;
    private static int currentPlayingChannelId;

    public PlayControlDelegate(PlayFragment playFragment) {
        this.playFragment = playFragment;
    }

    public void play(final int channelId) {
        currentPlayingChannelId = channelId;
        if(DoubanApplication.isPlaying)return;
		DoubanApplication.isPlaying = true;
		new PoolAsyncTask<Void,Void,Void>() {
			@Override protected Void doInBackground(Void... params) {
				if (cachedSongsList == null || cachedSongsList.size() == 0) {
					fetchNewSongsOrReportPlayInfo(ReportType.NULL,
							startInClassScope != null ? new Interval(startInClassScope, new DateTime()).toDuration().toPeriod().getSeconds() : 0
							, BitRate.HIGH);
					DoubanApplication.isPlaying = false;
					return null;
				}
				currentPlayingSong = cachedSongsList.remove();
				if (cachedSongsList.size() < WARNING_SIZE) {
					fetchNewSongsOrReportPlayInfo(ReportType.NEXT_QUEUE,
							startInClassScope != null ? new Interval(startInClassScope, new DateTime()).toDuration().toPeriod().getSeconds() : 0
							, BitRate.HIGH);
				}
				playFragment.getView().post(new Runnable() {
					@Override public void run() {
						songChangeListener.onPlayStart(currentPlayingSong);
					}
				});

				mp = new MediaPlayer();
				final DateTime start = new DateTime();
				startInClassScope = start;
				mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						stopPlaying();
						//send end report
						int seconds = new Interval(start, new DateTime()).toDuration().toPeriod().getSeconds();
						sendReport(ReportType.END, seconds, BitRate.HIGH);
						play(channelId);
					}
				});

				try {
					mp.setDataSource(currentPlayingSong.url);
					try {
						mp.prepare();
					} catch (IOException e) {
						e.printStackTrace();
					}
					mp.start();
				} catch (IOException e) {
				}
				return null;
			}
		}.execute();

    }

    private void sendReport(ReportType reportType,int playTime,BitRate bitRate) {
        fetchNewSongsOrReportPlayInfo(reportType,
                playTime
                ,bitRate);
    }

    public void skipOrBanOrFavASong(ReportType reportType){
        int seconds = new Interval(startInClassScope, new DateTime()).toDuration().toPeriod().getSeconds();
        if(reportType.isCutCurrentPlaying()){
            stopPlaying();
        }
        sendReport(reportType, seconds,BitRate.HIGH);
    }
    public void stopPlaying() {
        try {
            mp.release();
            mp = null;
        }catch (Exception e){
        }finally {
			DoubanApplication.isPlaying = false;
        }
    }

    private void fetchNewSongsOrReportPlayInfo(final ReportType reportType,int playTime,BitRate bitRate) {
        songQueueListener.songListNearlyEmptyOrNeedReport(reportType,currentPlayingSong==null?"":currentPlayingSong.sid,playTime, currentPlayingChannelId,bitRate
                ,new DouCallback(douban) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess() {
                cachedSongsList = douban.songs;
                if(!DoubanApplication.isPlaying)play(currentPlayingChannelId);
            }

            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(Douban.app, douban.mApiRespErrorCode.getMsg(), Toast.LENGTH_SHORT).show();
                //if 添加红心失败，恢复灰色心型
                if(reportType.equals(ReportType.FAV)){
                    playFragment.songActionListener.favFailed();
                }else if(reportType.equals(ReportType.BAN)){
                    //TODO:log to db,即使用户ban失败，也可以永远不在听到刚刚ban掉的歌曲
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });

    }


    public void setDouban(Douban douban) {
        this.douban = douban;
    }
    public interface ISongActionListener {
        void banFailed();
        void favFailed();
    }
	ISongChangeListener songChangeListener;

	public void setSongChangeListener(ISongChangeListener songChangeListener) {
		this.songChangeListener = songChangeListener;
	}

	public interface ISongChangeListener{
		void onPlayStart(Song currentPlayingSong);
	}

}
