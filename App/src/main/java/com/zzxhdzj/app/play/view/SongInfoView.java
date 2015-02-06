package com.zzxhdzj.app.play.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.song.Song;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class SongInfoView extends TableLayout {
    @InjectView(R.id.song_name_tv)
    TextView mSongNameTv;
    @InjectView(R.id.song_album_tv)
    TextView mSongAlbumTv;
    @InjectView(R.id.song_artist_tv)
    TextView mSongArtistTv;
    @InjectView(R.id.song_year_tv)
    TextView mSongYearTv;
    @InjectView(R.id.song_company_tv)
    TextView mSongCompanyTv;
    @InjectView(R.id.cd_cover)
    ImageView mCdCover;
    @InjectView(R.id.song_duration_tv)
    public TextView mSongDurationTv;
    private DisplayImageOptions options;

    public SongInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.inject(View.inflate(getContext(), R.layout.song_info, this));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();

    }

    public void bindView(Song song){
        mSongNameTv.setText(song.title);
        mSongNameTv.setSelected(true);
        mSongAlbumTv.setText(song.albumTitle);
        mSongCompanyTv.setSelected(true);
        mSongArtistTv.setText(song.artist);
        mSongArtistTv.setSelected(true);
        mSongYearTv.setText(song.publicTime);
        mSongYearTv.setSelected(true);
        mSongCompanyTv.setText(song.company);
        mSongCompanyTv.setSelected(true);
        ImageLoader.getInstance().displayImage(song.picture, mCdCover, options);
    }


}
