package com.wolve.game24h.ui.accelerate.model;

import java.io.Serializable;

public class UseAppModel implements Serializable {

    //应用名称
    private String appName;
    //应用封面
    private String appCover;
    //应用包名
    private String appPackage;
    //使用时长
    private double appUseTime;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppCover() {
        return appCover;
    }

    public void setAppCover(String appCover) {
        this.appCover = appCover;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public double getAppUseTime() {
        return appUseTime;
    }

    public void setAppUseTime(double appUseTime) {
        this.appUseTime = appUseTime;
    }
}
