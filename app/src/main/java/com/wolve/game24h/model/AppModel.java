package com.wolve.game24h.model;

import java.io.Serializable;

import androidx.databinding.BaseObservable;

public class AppModel extends BaseObservable implements Serializable {

    //应用名称
    public String appName;
    //icon
    public String appIcon;
    //应用包名
    public String appPackage;
    //使用时长
    public double appUseTime;
    //tag
    public String appTag;
    //category
    public String appCategory;
    //评分
    public int appScore;
    //应用封面地址
    public String appCover;
    //0: 图片封面 1:视频封面
    public int appCoverType;
}
