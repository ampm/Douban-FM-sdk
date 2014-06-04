package com.zzxhdzj.app.login.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.douban.R;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class LoginView extends LinearLayout {

    @InjectView(R.id.login_username_et)
    EditText mLoginUsernameEt;
    @InjectView(R.id.login_password_et)
    EditText mLoginPasswordEt;
    @InjectView(R.id.login_captcha_et)
    EditText mLoginCaptchaEt;
    @InjectView(R.id.captcha_image_view)
    ImageView mCaptchaImageView;

    @InjectView(R.id.login_skip)
    TextView mLoginSkip;
    @InjectView(R.id.login_submit_btn)
    Button mLoginSubmitBtn;
    @InjectView(R.id.loading)
    ProgressBar mLoading;

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.inject(View.inflate(getContext(), R.layout.login_view, this));
    }


    public EditText getmLoginUsernameEt() {
        return mLoginUsernameEt;
    }

    public EditText getmLoginPasswordEt() {
        return mLoginPasswordEt;
    }

    public EditText getmLoginCaptchaEt() {
        return mLoginCaptchaEt;
    }

    public ImageView getmCaptchaImageView() {
        return mCaptchaImageView;
    }

    public ProgressBar getmLoading() {
        return mLoading;
    }

    public TextView getmLoginSkip() {
        return mLoginSkip;
    }

    public Button getmLoginSubmitBtn() {
        return mLoginSubmitBtn;
    }
}
