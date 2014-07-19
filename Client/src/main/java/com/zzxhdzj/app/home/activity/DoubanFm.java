package com.zzxhdzj.app.home.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andlabs.androidutils.logging.L;
import com.zzxhdzj.app.DoubanFmApp;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.channels.ChannelFragment;
import com.zzxhdzj.app.home.DoubanFmDelegate;
import com.zzxhdzj.app.login.fragment.LoginFragment;
import com.zzxhdzj.app.play.delegate.PlayDelegate;
import com.zzxhdzj.app.play.fragment.PlayFragment;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.UserInfo;
import com.zzxhdzj.douban.modules.channel.Channel;

import static com.zzxhdzj.app.channels.ChannelFragment.ChannelFragmentListener;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFm extends FragmentActivity implements PlayerEngineListener, View.OnClickListener,ChannelFragmentListener {
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


    private DoubanFmDelegate doubanFmDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("onCreate");
        setContentView(R.layout.fragment_main);
        ButterKnife.inject(this);
        doubanFmDelegate = new DoubanFmDelegate(this);
        DoubanFmApp.getInstance().addPlayerEngineListener(this);
        pauseControlBtn();
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
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        L.d("onBackPressed");
    }

    public void showLoggedItems(UserInfo userInfo) {
        mMhzName.setText(String.format(getString(R.string.mhz_name_text), "私人"));
        mFavoredCount.setText(String.format(getString(R.string.favored_count), userInfo.playRecord.liked));
        mListenedCount.setText(String.format(getString(R.string.listened_count), userInfo.playRecord.played));
        mPersonalInfo.setVisibility(View.VISIBLE);
        mLeftControl.setVisibility(View.VISIBLE);
        registerViewClickListeners(
                mLeftBanButton,
                mLeftFavButton,
                mLeftSkipButton,
                mBtnChannelsGrid);
    }
    void registerViewClickListeners(View ... views){
        for (View view:views){
            view.setOnClickListener(this);
        }
    }
    void unRegisterViewClickListeners(View ... views){
        for (View view:views){
            view.setOnClickListener(null);
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
        DoubanFmApp.getInstance().getChannelFragmentListeners().clear();
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
            default:
                break;
        }
    }

    private void showChannelsFragment() {
        unRegisterViewClickListeners(
                mLeftBanButton,
                mLeftFavButton,
                mLeftSkipButton,
                mBtnChannelsGrid);
        mBottomControlLayout.setVisibility(View.INVISIBLE);
        DoubanFmApp.getInstance().getChannelFragmentListeners().add(this);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.root_view, new ChannelFragment())
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

    @Override
    public void onChannelSelected(Channel channel) {
    }
}
