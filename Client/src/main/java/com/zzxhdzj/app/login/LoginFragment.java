package com.zzxhdzj.app.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.zzxhdzj.app.login.view.LoginView;
import com.zzxhdzj.douban.Douban;
import com.zzxhdzj.douban.R;
import com.zzxhdzj.douban.modules.LoginParamsBuilder;
import com.zzxhdzj.http.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class LoginFragment extends Fragment {
    private LoginDelegate mLoginDelegate;
    @InjectView(R.id.login_card)
    LoginView mLoginCard;
    private Douban mDouban;
    private Callback mLoginCallback;
    private LoginListener mLoginListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDouban = new Douban(getActivity());
        mLoginCallback = new Callback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                if(mLoginListener !=null){
                    mLoginListener.onLogin();
                }
            }

            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(getActivity(), mDouban.mApiRespErrorCode.getMsg(), Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container,false);
        ButterKnife.inject(this,view);
        mLoginDelegate = new LoginDelegate(getActivity());
        mLoginDelegate.showCaptcha(mDouban, mLoginCard.getCaptchaImageView(), mLoginCard.getLoading());
        initializeLoginBtn(mDouban, mLoginCard, mLoginCallback);
        mLoginCard.getCaptchaImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginDelegate.showCaptcha(mDouban, mLoginCard.getCaptchaImageView(), mLoginCard.getLoading());
            }
        });
        return view;
    }
    public void initializeLoginBtn(final Douban douban, final LoginView loginCard, final Callback loginCallback) {
        loginCard.getLoginSubmitBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                douban.login(
                        LoginParamsBuilder.aLoginParams()
                                .withCaptchaId(douban.captchaId)
                                .withSource("radio")
                                .withRemember("on")
                                .withLoginMail(loginCard.getLoginUsernameEt().getText().toString())
                                .withPassword(loginCard.getLoginPasswordEt().getText().toString())
                                .withCaptcha(loginCard.getLoginCaptchaEt().getText().toString())
                                .build()
                        , loginCallback);
            }
        });
    }

    public void setmLoginListener(LoginListener mLoginListener) {
        this.mLoginListener = mLoginListener;
    }

    public interface LoginListener {
        public void onLogin();
    }
}
