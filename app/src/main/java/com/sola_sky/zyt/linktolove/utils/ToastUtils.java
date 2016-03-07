package com.sola_sky.zyt.linktolove.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Li on 2016/3/7.
 */
public class ToastUtils {
    private ToastUtils() {}

    public static void showToastShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
