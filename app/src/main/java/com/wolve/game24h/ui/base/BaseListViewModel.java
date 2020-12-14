package com.wolve.game24h.ui.base;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.TypeReference;
import com.wolve.libhttp.ApiResponse;
import com.wolve.libhttp.HttpClient;
import com.wolve.game24h.model.AppList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;

public class BaseListViewModel extends AbsViewModel<AppList> {

    private String listType;
    private int offset;
    private AtomicBoolean loadAfter = new AtomicBoolean();
    private MutableLiveData mutableLiveData = new MutableLiveData();

    @Override
    public DataSource createDataSource() {
        return new DataSource();
    }

    public MutableLiveData getMutableLiveData(){
        return mutableLiveData;
    }

    public void setListType(String listType){
        this.listType = listType;
    }


    private class DataSource extends ItemKeyedDataSource<Long, AppList>{
        @Override
        public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<AppList> callback) {
            loadData(0L, callback);
        }

        @Override
        public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<AppList> callback) {
            loadData(params.key, callback);
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<AppList> callback) {
            callback.onResult(Collections.emptyList());
        }

        private void loadData(Long requestKey, ItemKeyedDataSource.LoadCallback<AppList> callback) {
            if (requestKey > 0){
                loadAfter.set(true);
            }
            ApiResponse<List<AppList>> response = HttpClient.get("/type/queryAppList")
                    .addParam("listType", listType)
                    .addParam("offset", offset)
                    .responseType(new TypeReference<ArrayList<AppList>>(){}.getType()).execute();
            List<AppList> result = response.body == null ? Collections.emptyList() : response.body;
            callback.onResult(result);
            if (requestKey > 0){
                loadAfter.set(false);
                offset += result.size();
                ((MutableLiveData) getBoundaryPageData()).postValue(result.size() > 0);
            }else{
                offset = result.size();
            }
        }

        @NonNull
        @Override
        public Long getKey(@NonNull AppList item) {
            return item.appId;
        }
    }

    @SuppressLint("RestrictedApi")
    public void loadData(long typeId, ItemKeyedDataSource.LoadCallback callback) {
        if (typeId <= 0 || loadAfter.get()) {
            callback.onResult(Collections.emptyList());
            return;
        }
        ArchTaskExecutor.getIOThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                ((BaseListViewModel.DataSource) getDataSource()).loadData(typeId, callback);
            }
        });
    }
}
