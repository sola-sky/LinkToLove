package com.sola_sky.zyt.linktolove.utils;

import android.util.Log;

import com.sola_sky.zyt.linktolove.features.Login.LoginActivity;

/**
 * Created by Li on 2016/3/19.
 */
public class LogUtils {
    private static boolean DEBUG_FLAG = true;
    private LogUtils() {

    }

    public static void logd(String tag, String msg) {
        if (DEBUG_FLAG) {
            Log.d(tag, msg);
        }
    }
}
