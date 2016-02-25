package com.sola_sky.zyt.linktolove;

import android.widget.EditText;

import com.sola_sky.zyt.linktolove.features.Login.LoginActivity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by Li on 2016/2/25.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)

public class LoginActivityTest {

    @Test
    public void testPerformClick() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        boolean enable = loginActivity.findViewById(R.id.btn_login_login).isEnabled();
        Assert.assertEquals(false, enable);
    }

    @Test
    public void testAccountInput() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        ((EditText)loginActivity.findViewById(R.id.et_login_account)).setText("1");
     // ((EditText)loginActivity.findViewById(R.id.et_login_password)).setText("1");
        boolean enable = loginActivity.findViewById(R.id.btn_login_login).isEnabled();
        Assert.assertEquals(true, enable);
    }
}
