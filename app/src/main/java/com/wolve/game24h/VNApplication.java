package com.wolve.game24h;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.wolve.libhttp.HttpClient;

public class VNApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HttpClient.init("http://42.192.152.39:3000", null);
        Fresco.initialize(this);
    }
}
