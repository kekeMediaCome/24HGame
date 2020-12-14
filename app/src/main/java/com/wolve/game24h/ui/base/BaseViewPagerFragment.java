package com.wolve.game24h.ui.base;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.wolve.game24h.databinding.FragmentGameBinding;
import com.wolve.game24h.model.AppTab;
import com.wolve.game24h.ui.app.AppFragment;
import com.wolve.game24h.utils.VNAppConfig;

import java.util.ArrayList;
import java.util.List;

public class BaseViewPagerFragment extends Fragment {

    private FragmentGameBinding gameBinding;
    protected ViewPager2 viewPager;
    protected TabLayout tabLayout;
    private AppTab tabConfig;
    private ArrayList<AppTab.Tabs> tabs;

    private TabLayoutMediator mediator;

    public static AppFragment newInstance(String listType) {
        Bundle args = new Bundle();
        args.putString("listType", listType);
        AppFragment fragment = new AppFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gameBinding = FragmentGameBinding.inflate(inflater, container, false);
        return gameBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = gameBinding.viewPager;
        tabLayout = gameBinding.tabLayout;
        tabConfig = getTabConfig();
        tabs = new ArrayList<>();
        for (AppTab.Tabs tab : tabConfig.tabs) {
            if (tab.enable) {
                tabs.add(tab);
            }
        }

        //限制页面预加载
        viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //viewPager2默认只有一种类型的Adapter。FragmentStateAdapter
        //并且在页面切换的时候 不会调用子Fragment的setUserVisibleHint ，取而代之的是onPause(),onResume()
        viewPager.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return getTabFragment(position);
            }

            @Override
            public int getItemCount() {
                return tabs.size();
            }
        });
        if (tabConfig.tabGravity == -1){
            tabConfig.tabGravity = 1 << 1;
        }
        tabLayout.setTabGravity(tabConfig.tabGravity);
        mediator = new TabLayoutMediator(tabLayout, viewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setCustomView(makeTabView(position));
            }
        });
        mediator.attach();
        viewPager.registerOnPageChangeCallback(mPageChangeCallback);
        viewPager.post(() -> viewPager.setCurrentItem(tabConfig.select, false));
    }

    ViewPager2.OnPageChangeCallback mPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            int tabCount = tabLayout.getTabCount();
            for (int i = 0; i < tabCount; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                TextView customView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {

                    customView.setTextSize(tabConfig.activeSize);
                    customView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    customView.setTextSize(tabConfig.normalSize);
                    customView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    public Fragment getTabFragment(int position) {
        return AppFragment.newInstance(tabs.get(position).type);
    }

    public AppTab getTabConfig() {
        return VNAppConfig.getAppTab();
    }

    private View makeTabView(int position) {
        TextView tabView = new TextView(getContext());
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};

        int[] colors = new int[]{Color.parseColor(tabConfig.activeColor), Color.parseColor(tabConfig.normalColor)};
        ColorStateList stateList = new ColorStateList(states, colors);
        tabView.setTextColor(stateList);
        tabView.setText(tabs.get(position).title);
        tabView.setTextSize(tabConfig.normalSize);
        return tabView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.isAdded() && fragment.isVisible()) {
                fragment.onHiddenChanged(hidden);
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediator.detach();
        viewPager.unregisterOnPageChangeCallback(mPageChangeCallback);
        super.onDestroy();
    }
}