package com.wolve.game24h.ui.app;

import com.wolve.libnavannotation.FragmentDestination;
import com.wolve.game24h.model.AppTab;
import com.wolve.game24h.ui.base.BaseListFragment;
import com.wolve.game24h.ui.base.BaseViewPagerFragment;
import com.wolve.game24h.utils.VNAppConfig;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

@FragmentDestination(pageUrl = "main/tabs/app", asStarter = false)
public class AppFragment extends BaseViewPagerFragment {

    @Override
    public Fragment getTabFragment(int position) {
        AppTab.Tabs tab = getTabConfig().tabs.get(position);
        BaseListFragment fragment = BaseListFragment.newInstance(tab.type);
        return fragment;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public AppTab getTabConfig() {
        return VNAppConfig.getAppTab();
    }
}
