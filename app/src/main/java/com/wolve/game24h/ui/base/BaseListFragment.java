package com.wolve.game24h.ui.base;

import android.os.Bundle;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wolve.game24h.ui.app.AppListAdapter;
import com.wolve.game24h.MutableItemKeyedDataSource;
import com.wolve.game24h.model.AppList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;

public class BaseListFragment extends AbsListFragment<AppList, BaseListViewModel> {

    public static final String KEY_TYPE = "list_type";
    private String listType;

    public static BaseListFragment newInstance(String listType){
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, listType);
        BaseListFragment fragment = new BaseListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.removeItemDecorationAt(0);
        mViewModel.setListType(listType);
    }

    @Override
    public PagedListAdapter getAdapter() {
        listType = getArguments().getString(KEY_TYPE);
        AppListAdapter appListAdapter = new AppListAdapter(getContext());
        return appListAdapter;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        PagedList<AppList> currentList = getAdapter().getCurrentList();
        long appId = currentList == null ? 0 : currentList.get(currentList.size() - 1).appId;
        mViewModel.loadData(appId, new ItemKeyedDataSource.LoadCallback() {
            @Override
            public void onResult(@NonNull List data) {
                if (data != null && data.size() > 0) {
                    MutableItemKeyedDataSource<Long, AppList> mutableItemKeyedDataSource = new MutableItemKeyedDataSource<Long, AppList>((ItemKeyedDataSource) mViewModel.getDataSource()) {
                        @NonNull
                        @Override
                        public Long getKey(@NonNull AppList item) {
                            return item.appId;
                        }
                    };
                    mutableItemKeyedDataSource.data.addAll(currentList);
                    mutableItemKeyedDataSource.data.addAll(data);
                    PagedList<AppList> pagedList = mutableItemKeyedDataSource.buildNewPagedList(currentList.getConfig());
                    submitList(pagedList);
                } else {
                    finishRefresh(false);
                }
            }
        });

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mViewModel.getDataSource().invalidate();
    }
}
