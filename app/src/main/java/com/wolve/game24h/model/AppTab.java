package com.wolve.game24h.model;

import java.util.List;

public class AppTab {
    public int activeSize;
    public int normalSize;
    public String activeColor;
    public String normalColor;
    public int select;
    public int tabGravity;
    public List<Tabs> tabs;

    public static class Tabs {

        public String title;
        public int index;
        public String type;
        public boolean enable;
    }
}
