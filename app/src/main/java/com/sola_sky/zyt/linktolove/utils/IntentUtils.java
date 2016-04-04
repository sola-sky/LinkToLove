package com.sola_sky.zyt.linktolove.utils;

import android.content.Context;
import android.content.Intent;

import com.sola_sky.zyt.linktolove.features.home.HomeActivity;
import com.sola_sky.zyt.linktolove.features.home.MainActivity;
import com.sola_sky.zyt.linktolove.features.test.DragActivity;
import com.sola_sky.zyt.linktolove.features.test.MyDragActivity;
import com.sola_sky.zyt.linktolove.features.test.MyViewGroupActivity;
import com.sola_sky.zyt.linktolove.features.userinfo.UserInfoActivity;

/**
 * Created by Li on 2016/3/6.
 */

public class IntentUtils {
    private IntentUtils() {

    }

    public static void startHome(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void startUserInfo(Context context) {
        Intent intent = new Intent(context, UserInfoActivity.class);
        context.startActivity(intent);
    }

    public static void startSetting(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void startMyGroupView(Context context) {
        Intent intent = new Intent(context, MyViewGroupActivity.class);
        context.startActivity(intent);
    }
    public static void startMyDragView(Context context) {
        Intent intent = new Intent(context, DragActivity.class);
        context.startActivity(intent);
    }

    public static void startDragView(Context context) {
        Intent intent = new Intent(context, MyDragActivity.class);
        context.startActivity(intent);
    }
}
