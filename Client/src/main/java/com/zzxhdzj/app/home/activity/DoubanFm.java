package com.zzxhdzj.app.home.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.andlabs.androidutils.logging.L;
import com.zzxhdzj.app.DoubanApplication;
import com.zzxhdzj.app.base.media.PlayerEngineListener;
import com.zzxhdzj.app.home.DoubanFmDelegate;
import com.zzxhdzj.app.login.fragment.LoginFragment;
import com.zzxhdzj.app.play.delegate.PlayDelegate;
import com.zzxhdzj.app.play.fragment.PlayFragment;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFm extends FragmentActivity implements PlayerEngineListener{
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
    public
    ImageView mLeftFavButton;
    private DoubanFmDelegate doubanFmDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("onCreate");
        setContentView(R.layout.main);
        ButterKnife.inject(this);
        doubanFmDelegate = new DoubanFmDelegate(this);
        DoubanApplication.getInstance().addPlayerEngineListener(this);
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
    }

    public void showLoggedItems(UserInfo userInfo) {
        mMhzName.setText(String.format(getString(R.string.mhz_name_text), "私人"));
        mFavoredCount.setText(String.format(getString(R.string.favored_count), userInfo.playRecord.liked));
        mListenedCount.setText(String.format(getString(R.string.listened_count), userInfo.playRecord.played));
        mPersonalInfo.setVisibility(View.VISIBLE);
        mLeftControl.setVisibility(View.VISIBLE);
        mLeftSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseControlBtn();
                PlayDelegate.getInstance().skip();
            }
        });
        mLeftBanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayDelegate.getInstance().ban();
            }
        });
        mLeftFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLeftFavButton.isActivated()){
                    PlayDelegate.getInstance().unfav();
                    DoubanApplication.getInstance().getCurrentPlayingSong().like="0";
                    mLeftFavButton.setActivated(false);
                }else {
                    PlayDelegate.getInstance().fav();
                    DoubanApplication.getInstance().getCurrentPlayingSong().like="1";
                    mLeftFavButton.setActivated(true);
                }
            }
        });
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
        if(DoubanApplication.getInstance().getCurrentPlayingSong()!=null&&DoubanApplication.getInstance().getCurrentPlayingSong().isLiked()){
            mLeftFavButton.setActivated(true);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        L.d("onDestroy");
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
        resumeControlBtn();
    }

}
