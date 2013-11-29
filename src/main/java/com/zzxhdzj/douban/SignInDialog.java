package com.zzxhdzj.douban;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.zzxhdzj.douban.api.auth.AuthenticationGateway;
import com.zzxhdzj.http.Callback;


/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 10/27/13
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignInDialog extends Dialog {

    private final AuthenticationGateway mAuthenticationGateway;
    private final Douban douban;
    ProgressDialog d;
    private Callback mCallback;
    private Button mSignInButton;
    private Button gobackButton;

    public SignInDialog(Douban douban, Context context, AuthenticationGateway mAuthenticationGateway, Callback callback) {
        super(context, android.R.style.Theme_Light_NoTitleBar);
        this.mAuthenticationGateway = mAuthenticationGateway;
        this.mCallback = callback;
        this.douban = douban;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_dialog);
        final EditText usernameEditText = (EditText) findViewById(R.id.text_username);
        final EditText passwordEditText = (EditText) findViewById(R.id.text_password);
        mSignInButton = (Button) findViewById(R.id.button_login);
        gobackButton = (Button) findViewById(R.id.gobackbt);
        gobackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        new ViewEnablingTextWatcher(mSignInButton, usernameEditText, passwordEditText);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignInButton.setEnabled(false);
                d = new ProgressDialog(getContext());
                d.setMessage(getContext().getString(R.string.loading));
                d.show();
                Callback callback = new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure() {
                    }
                };
//                mAuthenticationGateway.signIn(Constants.APP_NAME,Constants.APP_VERSION,usernameEditText.getText().toString(),passwordEditText.getText().toString(),callback);
            }
        });
    }

}
