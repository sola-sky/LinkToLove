package com.sola_sky.zyt.linktolove.features.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sola_sky.zyt.linktolove.R;
import com.sola_sky.zyt.linktolove.app.BaseActivity;
import com.sola_sky.zyt.linktolove.features.PermissionCallback;
import com.sola_sky.zyt.linktolove.utils.IntentUtils;
import com.sola_sky.zyt.linktolove.utils.PermissionUtils;

public class HomeActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, PermissionCallback {

    private static final int READ_SD = 100;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initMember() {

    }

    @Override
    public void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavView = (NavigationView) findViewById(R.id.nav_view);
    }

    @Override
    public void initWidget() {
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.open_drawer_activity_home, R.string.close_drawer_activity_home);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void initOther() {
        PermissionUtils.needPermission(this, READ_SD,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}
        ,this);
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == READ_SD) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onSuccessPermission();
                } else {
                    onFailedPermission();
                }
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sub1:
                IntentUtils.startSetting(this);
                break;
            case R.id.sub2:
                break;
            case R.id.send:
                break;
            case R.id.share:
                break;
            case R.id.user_info:
                IntentUtils.startUserInfo(this);
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onSuccessPermission() {

    }

    @Override
    public void onFailedPermission() {

    }
}
