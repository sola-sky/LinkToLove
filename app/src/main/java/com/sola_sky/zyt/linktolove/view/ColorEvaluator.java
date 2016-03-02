package com.sola_sky.zyt.linktolove.view;

import android.animation.TypeEvaluator;
import android.content.pm.PackageManager;

/**
 * Created by Li on 2016/2/29.
 */
public class ColorEvaluator implements TypeEvaluator {
    private int mCurrentRed = -1;

    public void setmCurrentRed(int mCurrentRed) {
        this.mCurrentRed = mCurrentRed;
    }

    public void setmCurrentGreen(int mCurrentGreen) {
        this.mCurrentGreen = mCurrentGreen;
    }

    public void setmCurrentBlue(int mCurrentBlue) {
        this.mCurrentBlue = mCurrentBlue;
    }

    private int mCurrentGreen = -1;
    private int mCurrentBlue = -1;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        String startColor = (String) startValue;
        System.out.println("startColor:" + startColor);
        String endColor = (String) endValue;
        System.out.println("endColor");
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }

        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {

            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff, redDiff + greenDiff,
                    fraction);
        }

        String currentColor = "#" + getHexString(mCurrentRed) + getHexString(mCurrentGreen)
                + getHexString(mCurrentBlue);
        System.out.println(mCurrentBlue);
        System.out.println(mCurrentRed);
        System.out.println(mCurrentGreen);
        System.out.println(currentColor);
        return currentColor;
    }

    private int getCurrentColor(int startColor, int endColor, int colorDiff, int offset,
                                float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int)(startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int)(startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        if (currentColor > 255) {
            currentColor = 255;
        } else if (currentColor < 0) {
            currentColor = 0;
        }
        return currentColor;
    }

    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }
}
