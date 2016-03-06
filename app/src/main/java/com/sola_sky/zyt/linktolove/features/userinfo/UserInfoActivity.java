package com.sola_sky.zyt.linktolove.features.userinfo;

import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.app.BaseActivity;

public class UserInfoActivity extends BaseActivity {

    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsing;
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void initMember() {

    }

    @Override
    public void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    @Override
    public void initWidget() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCollapsing.setTitle(getString(R.string.title_activity_user_info));

    }

    @Override
    public void initOther() {

    }
}
