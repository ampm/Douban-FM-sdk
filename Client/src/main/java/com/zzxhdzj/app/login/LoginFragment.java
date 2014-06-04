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
import com.zzxhdzj.http.Callback;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy@snda.com
 * Date: 6/1/14
 * To change this template use File | Settings | File Templates.
 */
public class LoginFragment extends Fragment {
    LoginDelegate loginDelegate;
    @InjectView(R.id.login_card)
    LoginView mLoginCard;
    private Douban douban;
    private Callback loginCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginCallback = new Callback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess() {
                super.onSuccess();
                if(getActivity()!=null&&getActivity() instanceof LoginCallback){
                    ((LoginCallback) getActivity()).onLogin();
                }
            }

            @Override
            public void onFailure() {
                super.onFailure();
                Toast.makeText(getActivity(), douban.mApiRespErrorCode.getMsg(), Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container,false);
        ButterKnife.inject(this,view);
        loginDelegate = new LoginDelegate(getActivity());
        loginDelegate.showCaptcha(douban, mLoginCard.getmCaptchaImageView(), mLoginCard.getmLoading());
        loginDelegate.initializeLoginBtn(douban, mLoginCard, loginCallback);
        mLoginCard.getmCaptchaImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDelegate.showCaptcha(douban, mLoginCard.getmCaptchaImageView(), mLoginCard.getmLoading());
            }
        });
        return view;
    }

    public void setDouban(Douban douban) {
        this.douban = douban;
    }
    public interface LoginCallback{
        public void onLogin();
    }
}
