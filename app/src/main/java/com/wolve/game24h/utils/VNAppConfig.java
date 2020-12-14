package com.wolve.game24h.utils;

import android.content.res.AssetManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wolve.libcommon.global.VNAppGlobals;
import com.wolve.game24h.model.BottomBar;
import com.wolve.game24h.model.Destination;
import com.wolve.game24h.model.AppTab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class VNAppConfig {
    private static HashMap<String, Destination> sDestConfig;
    private static BottomBar bottomBar;
    private static AppTab appTab;

    public static HashMap<String, Destination> getDestConfig() {
        if (sDestConfig == null) {
            String content = parseFile("destination.json");
            sDestConfig = JSON.parseObject(content, new TypeReference<HashMap<String, Destination>>() {
            });
        }
        return sDestConfig;
    }

    public static BottomBar getBottomBarConfig() {
        if (bottomBar == null) {
            String content = parseFile("main_tabs_config.json");
            bottomBar = JSON.parseObject(content, BottomBar.class);
        }
        return bottomBar;
    }

    public static AppTab getAppTab(){
        if (appTab == null) {
            String content = parseFile("app_tabs_config.json");
            appTab = JSON.parseObject(content, AppTab.class);
            Collections.sort(appTab.tabs, new Comparator<AppTab.Tabs>() {
                @Override
                public int compare(AppTab.Tabs o1, AppTab.Tabs o2) {
                    return o1.index < o2.index ? -1 : 1;
                }
            });
        }
        return appTab;
    }


    private static String parseFile(String fileName) {
        AssetManager assets = VNAppGlobals.getApplication().getAssets();
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            is = assets.open(fileName);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {

            }
        }

        return builder.toString();
    }
}
