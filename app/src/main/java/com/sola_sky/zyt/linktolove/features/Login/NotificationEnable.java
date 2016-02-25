package com.sola_sky.zyt.linktolove.features.Login;

import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Li on 2016/2/25.
 */
public class NotificationEnable {
    private boolean mFirst;
    private boolean mSencond;

    private EditText mEt1;
    private EditText mEt2;
    private Button mBtn;

    NotificationEnable(EditText editText1, EditText editText2, Button btn) {
        mEt1 = editText1;
        mEt2 = editText2;
        mBtn = btn;
    }

    public void setEtState(EditText et, boolean is) {
        if (et == mEt1) {
            mFirst = is;
        } else if (et == mEt2) {
            mSencond = is;
        }
        notifyBtnEnable();
    }

    public void notifyBtnEnable() {
        if (mFirst && mSencond) {
            mBtn.setEnabled(true);
        } else {
            mBtn.setEnabled(false);
        }
    }
}
