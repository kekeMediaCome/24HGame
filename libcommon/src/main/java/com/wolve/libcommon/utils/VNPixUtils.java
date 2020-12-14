package com.wolve.libcommon.utils;

import android.util.DisplayMetrics;

import com.wolve.libcommon.global.VNAppGlobals;

public class VNPixUtils {

    public static int dp2px(int dpValue) {
        DisplayMetrics metrics = VNAppGlobals.getApplication().getResources().getDisplayMetrics();
        return (int) (metrics.density * dpValue + 0.5f);
    }

    public static int getScreenWidth() {
        DisplayMetrics metrics = VNAppGlobals.getApplication().getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    public static int getScreenHeight() {
        DisplayMetrics metrics = VNAppGlobals.getApplication().getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }
}
