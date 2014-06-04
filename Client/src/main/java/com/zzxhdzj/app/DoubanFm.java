package com.zzxhdzj.app;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.app.login.LoginFragment;
import com.zzxhdzj.app.play.PlayFragment;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.api.BitRate;
import com.zzxhdzj.douban.api.channels.local.ChannelHelper;
import com.zzxhdzj.douban.db.DbTables;
import com.zzxhdzj.douban.modules.channel.Channel;
import com.zzxhdzj.douban.modules.channel.ChannelBuilder;
import com.zzxhdzj.http.Callback;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFm extends FragmentActivity implements LoginFragment.LoginCallback,PlayFragment.SongQueueListener {
    private static final int QUERY_CHANNEL = 1;
    @InjectView(R.id.mhz_name)
    TextView mMhzName;
    @InjectView(R.id.listened_count)
    TextView mListenedCount;
    @InjectView(R.id.favored_count)
    TextView mFavoredCount;
    @InjectView(R.id.personal_info)
    LinearLayout mPersonalInfo;
    @InjectView(R.id.left_control)
    LinearLayout mLeftControl;
    @InjectView(R.id.left_skip_button)
    ImageView mLeftSkipButton;
    private Douban douban;
    private ArrayList<Channel> channelArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);
        douban = new Douban(this);
       new ChannelHelper(this).queryChannels(new LoaderManager.LoaderCallbacks<Cursor>() {
           @Override
           public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
               if (loaderId == QUERY_CHANNEL) {
                   return new CursorLoader(DoubanFm.this, DbTables.ChannelTable.CONTENT_URI,
                           Channel.RECEIPT_PROJECTION, null, null, null);
               }
               return null;
           }

           @Override
           public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
               if(loader.getId()==QUERY_CHANNEL){
                   dumpCursorToChannels(data);
               }
           }

           @Override
           public void onLoaderReset(Loader<Cursor> loader) {

           }

           private void dumpCursorToChannels(Cursor data) {
               channelArrayList = new ArrayList<Channel>();
               while (data.moveToNext()){
                   channelArrayList.add(ChannelBuilder.aChannel().withId(data.getInt(Channel.CHANNEL_ID_INDEX))
                           .withName(data.getString(Channel.NAME_INDEX))
                           .build());
               }
           }

       });
        if (douban.isLogged()) {
           if(douban.songs==null){
               queryFavSongs();
           }
           showAuthItems();
           showPlayFragment();
        }else {
            showLoginFragment();
        }
    }

    private void showLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setDouban(douban);
        ft.replace(R.id.dou_content, loginFragment);
        ft.commit();
    }

    private void showPlayFragment() {
        Toast.makeText(this,"play....",Toast.LENGTH_SHORT).show();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        PlayFragment loginFragment = new PlayFragment();
        loginFragment.setSongQueueListener(this);
        loginFragment.setDouban(douban);
        ft.replace(R.id.dou_content, loginFragment,PlayFragment.TAG);
        ft.commit();
    }

    @Override
    public void onLogin() {
        showPlayFragment();
        showAuthItems();
    }

    private void showAuthItems() {
        mMhzName.setText(String.format(getString(R.string.mhz_name_text),"私人"));
        mFavoredCount.setText(String.format(getString(R.string.favored_count),douban.getUserInfo().playRecord.liked));
        mListenedCount.setText(String.format(getString(R.string.listened_count), douban.getUserInfo().playRecord.played));
        mPersonalInfo.setVisibility(View.VISIBLE);
        mLeftControl.setVisibility(View.VISIBLE);
        mLeftSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(PlayFragment.TAG);
                if(fragmentByTag!=null){
                    PlayFragment playFragment = (PlayFragment) fragmentByTag;
                    playFragment.getPlayDelegate().playNext();
                }

            }
        });
    }

    private void queryFavSongs() {
        douban.songsOfPrivateChannels(BitRate.HIGH, new DouCallback(douban) {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess() {
                super.onSuccess();

                if (isPlayFragmentAdded()) {
                    showPlayFragment();
                }
            }

            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(DoubanFm.this, douban.mApiRespErrorCode.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }

        });
    }

    private boolean isPlayFragmentAdded() {
        Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(PlayFragment.TAG);
        return fragmentByTag!=null&&!fragmentByTag.isAdded();
    }

    @Override
    public void songListNearlyEmpty(Callback callback) {
        douban.songsOfPrivateChannels(BitRate.HIGH, callback);
    }



}
