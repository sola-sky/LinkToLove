package com.sola_sky.zyt.linktolove.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Li on 2016/3/2.
 */
public class DimensionUtils {

    private DimensionUtils() {

    }
    public static float dp2px(Context context, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,context.getResources()
        .getDisplayMetrics());
    }

    public static float sp2px(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources()
        .getDisplayMetrics());
    }

    public static float px2dp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }

    public static float px2sp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }
}
