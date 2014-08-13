package com.zzxhdzj.app.home.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andlabs.androidutils.logging.L;
import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.base.service.PlayerService;
import com.zzxhdzj.app.channels.ChannelCategoryFragment;
import com.zzxhdzj.app.channels.ChannelListFragment;
import com.zzxhdzj.app.home.DoubanFmDelegate;
import com.zzxhdzj.app.login.fragment.LoginFragment;
import com.zzxhdzj.app.play.delegate.PlayDelegate;
import com.zzxhdzj.app.play.fragment.PlayFragment;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.UserInfo;
import com.zzxhdzj.douban.modules.channel.Channel;

import java.beans.Visibility;


/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFm extends FragmentActivity implements PlayerEngineListener, ChannelCategoryFragment.ChannelFragmentListener, View.OnClickListener {
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
    @InjectView(R.id.left_ban_button)
    ImageView mLeftBanButton;
    @InjectView(R.id.left_fav_button)
    public ImageView mLeftFavButton;
    @InjectView(R.id.about_me)
    ImageButton mAboutMe;
    @InjectView(R.id.root_view)
    FrameLayout mRootView;
    @InjectView(R.id.btn_channels_grid)
    ImageButton mBtnChannelsGrid;
    @InjectView(R.id.bottom_control_layout)
    LinearLayout mBottomControlLayout;
//    @InjectView(R.id.shortcut_control)
//    RadioGroup mShortcutControl;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equalsIgnoreCase(PlayerService.ACTION_CLOSE_APP)){
                finish();
            }
        }
    };
    private DoubanFmDelegate doubanFmDelegate;
    private ChannelChangedReceiver channelChangedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("onCreate");
        setContentView(R.layout.fragment_main);
        ButterKnife.inject(this);
        doubanFmDelegate = new DoubanFmDelegate(this);
        DoubanFmApp.getInstance().addPlayerEngineListener(this);
        pauseControlBtn();
        IntentFilter filters = new IntentFilter();
        filters.addAction(ChannelListFragment.ACTION_CHANNEL_SELECTED);
        channelChangedReceiver = new ChannelChangedReceiver();
        DoubanFmApp.getInstance().registerReceiver(channelChangedReceiver,filters);
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction(PlayerService.ACTION_CLOSE_APP);
        registerReceiver(broadcastReceiver, intentfilter);
    }

    public void showLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setLoginListener(doubanFmDelegate);
        ft.replace(R.id.dou_content, loginFragment);
        ft.commit();
    }

    public void showPlayFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        PlayFragment playFragment = new PlayFragment();
        ft.replace(R.id.dou_content, playFragment, PlayFragment.TAG);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        L.d("onBackPressed");
    }

    public void showLoggedItems(UserInfo userInfo) {
        String channelName = Douban.getSharedPreferences().getString(PlayFragment.LAST_OPENED_CHANNEL_NAME,"私人");
        mMhzName.setText(String.format(getString(R.string.mhz_name_text),channelName));
        mFavoredCount.setText(String.format(getString(R.string.favored_count), userInfo.playRecord.liked));
        mListenedCount.setText(String.format(getString(R.string.listened_count), userInfo.playRecord.played));
        mPersonalInfo.setVisibility(View.VISIBLE);
        mLeftControl.setVisibility(View.VISIBLE);
        mBottomControlLayout.setVisibility(View.VISIBLE);
//        mShortcutControl.setVisibility(View.VISIBLE);
        registerViewClickListeners(
                mLeftBanButton,
                mLeftFavButton,
                mLeftSkipButton,
                mBtnChannelsGrid,
                mAboutMe);

    }
    void registerViewClickListeners(View ... views){
        for (View view:views){
            view.setOnClickListener(this);
        }
    }
    private void pauseControlBtn() {
        mLeftSkipButton.setEnabled(false);
        mLeftFavButton.setEnabled(false);
        mLeftBanButton.setEnabled(false);
        mLeftFavButton.setActivated(false);

    }
    private void resumeControlBtn() {
        mLeftSkipButton.setEnabled(true);
        mLeftFavButton.setEnabled(true);
        mLeftBanButton.setEnabled(true);
    }

    @Override
    public boolean shouldPlay() {
        return true;
    }

    @Override
    public void onSongChanged() {
        resumeControlBtn();
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
    protected void onPause() {
        super.onPause();
        L.d("onPause");
    }

    @Override
    public void onBan() {

    }

    @Override
    public void onSongProgress(int duration, int playDuration) {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("onDestroy");
        if(channelChangedReceiver!=null){
            DoubanFmApp.getInstance().unregisterReceiver(channelChangedReceiver);
        }
        this.unregisterReceiver(broadcastReceiver);
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        L.d("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.d("onResume");
        doubanFmDelegate.prepare();
        if(DoubanFmApp.getInstance().getCurrentPlayingSong()!=null){
            resumeControlBtn();
            if(DoubanFmApp.getInstance().getCurrentPlayingSong().isLiked()){
                mLeftFavButton.setActivated(true);
            }
        }else {
            mLeftFavButton.setActivated(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_skip_button:
                pauseControlBtn();
                PlayDelegate.getInstance().skip();
                break;
            case R.id.left_ban_button:
                pauseControlBtn();
                PlayDelegate.getInstance().ban();
                break;
            case R.id.left_fav_button:
                if(mLeftFavButton.isActivated()){
                    PlayDelegate.getInstance().unfav();
                    DoubanFmApp.getInstance().getCurrentPlayingSong().like="0";
                    mLeftFavButton.setActivated(false);
                }else {
                    PlayDelegate.getInstance().fav();
                    DoubanFmApp.getInstance().getCurrentPlayingSong().like="1";
                    mLeftFavButton.setActivated(true);
                }
                break;
            case R.id.btn_channels_grid:
                showChannelsFragment();
                break;
            case R.id.about_me:
                showOrHidePersonnalinfo();
            default:
                break;
        }
    }

    private void showOrHidePersonnalinfo() {
        mPersonalInfo.setVisibility(mPersonalInfo.getVisibility()==View.VISIBLE? View.GONE:View.VISIBLE);
    }

    //DoubanFmApp.getInstance().getChannelFragmentListeners().add(this);
    private void showChannelsFragment() {
        mBottomControlLayout.setVisibility(View.INVISIBLE);
        ChannelCategoryFragment fragment = new ChannelCategoryFragment();
        fragment.setListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_view, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onChannelFragmentDestroy() {
        registerViewClickListeners(mLeftBanButton,
                mLeftFavButton,
                mLeftSkipButton,
                mBtnChannelsGrid);
        mBottomControlLayout.setVisibility(View.VISIBLE);
    }
    private class ChannelChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if(intent.getAction().equals(ChannelListFragment.ACTION_CHANNEL_SELECTED)){
                Channel channel = null;
                if (intent.getSerializableExtra(ChannelListFragment.SELECTED_CHANNEL) != null) {
                    channel = (Channel) intent.getSerializableExtra(ChannelListFragment.SELECTED_CHANNEL);
                }
                final Channel finalChannel = channel;
                mMhzName.post(new Runnable() {
                    @Override
                    public void run() {
                        if(finalChannel !=null){
                            mMhzName.setText(finalChannel.name);
                        }
                    }
                });
            }
        }
    }

}
