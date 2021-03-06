package com.sola_sky.zyt.linktolove.features.Login;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.app.BaseActivity;
import com.sola_sky.zyt.linktolove.utils.IntentUtils;

public class LoginActivity extends BaseActivity implements LoginPresenter.View{

    private Button mLoginBtn;
    private EditText mAccountEt;
    private EditText mPasswordEt;

    private LinearLayout mRootLly;

    private LoginPresenter mPresenter;

    private NotificationEnable mBtnEnable;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initMember() {
        mPresenter = new LoginPresenterImpl(this, new UserInfo(null, null));
    }

    @Override
    public void findWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

   //     mRootLly = (LinearLayout)findViewById(R.id.lly_root);
        mAccountEt = (EditText) findViewById(R.id.et_login_account);
        mPasswordEt = (EditText) findViewById(R.id.et_login_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login_login);
        mBtnEnable = new NotificationEnable(mAccountEt, mPasswordEt, mLoginBtn);

    }

    @Override
    public void initWidget() {

        mAccountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mBtnEnable.setEtState(mAccountEt, true);
                } else {
                    mBtnEnable.setEtState(mAccountEt, false);
                }
            }
        });

        mPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mBtnEnable.setEtState(mPasswordEt, true);
                } else {
                    mBtnEnable.setEtState(mPasswordEt, false);
                }
            }
        });


        mLoginBtn.setEnabled(false);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });
    }

    @Override
    public void initOther() {

    }

    @Override
    public void loginSuccess(String msg) {
        IntentUtils.startHome(this);
        finish();
    }

    @Override
    public void loginError() {

    }
}
