package com.sola_sky.zyt.linktolove.features.Login;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.app.BaseActivity;

public class LoginActivity extends BaseActivity {

    private Button mLoginBtn;
    private EditText mAccountEt;
    private EditText mPasswordEt;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initMember() {

    }

    @Override
    public void findWidget() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccountEt = (EditText) findViewById(R.id.et_login_account);
        mPasswordEt = (EditText) findViewById(R.id.et_login_password);
        mLoginBtn = (Button) findViewById(R.id.btn_login_login);
    }

    @Override
    public void initWidget() {

    }

    @Override
    public void initOther() {

    }
}
