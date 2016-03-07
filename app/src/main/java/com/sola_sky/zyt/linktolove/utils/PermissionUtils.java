package com.sola_sky.zyt.linktolove.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.sola_sky.zyt.linktolove.features.PermissionCallback;

/**
 * Created by Li on 2016/3/7.
 */
public class PermissionUtils {
    private PermissionUtils() {}

    public static void needPermission(final Activity activity, final int request,
                                      final String[] permissions, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            callback.onSuccessPermission();
        } else {
            if (ContextCompat.checkSelfPermission(activity, permissions[0])
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                    ToastUtils.showToastLong(activity, "reason");
                    Handler handler = new Handler(activity.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ActivityCompat.requestPermissions(activity, permissions, request);
                        }
                    }, 3000);
                } else {
                    ActivityCompat.requestPermissions(activity, permissions, request);
                }
            } else {
                callback.onSuccessPermission();
            }
        }
    }
}
