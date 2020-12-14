package com.wolve.game24h.model;

import java.util.List;

public class BottomBar {
    public String activeColor;
    public String inActiveColor;
    public List<Tab> tabs;
    public int selectTab;//底部导航栏默认选中项

    public static class Tab {
        public int size;
        public boolean enable;
        public int index;
        public String pageUrl;
        public String title;
        public String tintColor;
    }
}
