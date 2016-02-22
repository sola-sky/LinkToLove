package com.sola_sky.zyt.linktolove.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Li on 2016/2/23.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMember();
        setContentView(getLayoutId());
        initWidget();
        findWidget();
        initOther();
    }

    public abstract int getLayoutId();
    public abstract void initMember();
    public abstract void findWidget();
    public abstract void initWidget();
    public abstract void initOther();

}
