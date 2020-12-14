package com.wolve.game24h.model;

import android.text.TextUtils;

import androidx.annotation.Nullable;

public class AppList extends AppModel{

    public String listType;
    public long appId;

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || !(obj instanceof AppList))
            return false;
        AppList newOne = (AppList) obj;
        return appId == newOne.appId
                && TextUtils.equals(appName, newOne.appName)
                && TextUtils.equals(appIcon, newOne.appIcon)
                && TextUtils.equals(appPackage, newOne.appPackage)
                && TextUtils.equals(appTag, newOne.appTag)
                && TextUtils.equals(appCategory, newOne.appCategory)
                && TextUtils.equals(appCover, newOne.appCover)
                && TextUtils.equals(listType, newOne.listType)
                && appUseTime == newOne.appUseTime
                && appScore == newOne.appScore
                && appCoverType == newOne.appCoverType;
    }
}
