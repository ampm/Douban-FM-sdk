package com.zzxhdzj.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.app.login.LoginFragment;
import com.zzxhdzj.app.play.PlayFragment;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.ReportType;
import com.zzxhdzj.douban.modules.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 3/29/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanFm extends FragmentActivity {
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
    ImageView mLeftFavButton;
    private DoubanFmDelegate doubanFmDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);
    }

    public void showLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setLoginListener(doubanFmDelegate);
        ft.replace(R.id.dou_content, loginFragment);
        ft.commit();
    }

    protected void showPlayFragment(PlayFragment.SongQueueListener listener) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragmentByTag = fm.findFragmentByTag(PlayFragment.TAG);
		if(fragmentByTag==null){
			PlayFragment playFragment = new PlayFragment();
			playFragment.setSongQueueListener(listener);
			playFragment.setDouban(doubanFmDelegate.getDouban());
			playFragment.setSongActionListener(doubanFmDelegate);
			ft.replace(R.id.dou_content, playFragment,PlayFragment.TAG);
			ft.commit();
		}
    }



    public void showLoggedItems(UserInfo userInfo) {
        mMhzName.setText(String.format(getString(R.string.mhz_name_text),"私人"));
        mFavoredCount.setText(String.format(getString(R.string.favored_count),userInfo.playRecord.liked));
        mListenedCount.setText(String.format(getString(R.string.listened_count), userInfo.playRecord.played));
        mPersonalInfo.setVisibility(View.VISIBLE);
        mLeftControl.setVisibility(View.VISIBLE);
        mLeftSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(PlayFragment.TAG);
                if(fragmentByTag!=null){
                    PlayFragment playFragment = (PlayFragment) fragmentByTag;
                    playFragment.getPlayControlDelegate().skipOrBanOrFavASong(ReportType.SKIP);
                }

            }
        });
        mLeftBanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(PlayFragment.TAG);
                if(fragmentByTag!=null){
                    PlayFragment playFragment = (PlayFragment) fragmentByTag;
                    playFragment.getPlayControlDelegate().skipOrBanOrFavASong(ReportType.BAN);
                }
            }
        });
        mLeftFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(PlayFragment.TAG);
                if(fragmentByTag!=null){
                    PlayFragment playFragment = (PlayFragment) fragmentByTag;
                    playFragment.getPlayControlDelegate().skipOrBanOrFavASong(ReportType.FAV);
                    mLeftBanButton.setPressed(true);
                }
            }
        });
    }

	@Override protected void onStart() {
		super.onStart();
		doubanFmDelegate = new DoubanFmDelegate(this);
		doubanFmDelegate.prepare();
		mMhzName.post(new Thread(){
			@Override public void run() {
				super.run();
				doubanFmDelegate.updateStaticChannelInfo();
				doubanFmDelegate.updateDynamicChannels();
			}
		});
	}
}
