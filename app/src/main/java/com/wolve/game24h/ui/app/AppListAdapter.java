package com.wolve.game24h.ui.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolve.libcommon.extention.AbsPagedListAdapter;
import com.wolve.game24h.databinding.LayoutAppListItemBinding;
import com.wolve.game24h.model.AppList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class AppListAdapter extends AbsPagedListAdapter<AppList, AppListAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;

    public AppListAdapter(Context context){
        super(new DiffUtil.ItemCallback<AppList>() {
            @Override
            public boolean areItemsTheSame(@NonNull AppList oldItem, @NonNull AppList newItem) {
                return oldItem.appId == newItem.appId;
            }

            @Override
            public boolean areContentsTheSame(@NonNull AppList oldItem, @NonNull AppList newItem) {
                return oldItem.equals(newItem);
            }
        });
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected AppListAdapter.ViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        LayoutAppListItemBinding itemBinding = LayoutAppListItemBinding.inflate(mInflater, parent, false);
        return new ViewHolder(itemBinding.getRoot(), itemBinding);
    }

    @Override
    protected void onBindViewHolder2(AppListAdapter.ViewHolder holder, int position) {
        final AppList item = getItem(position);
        holder.bindData(item);
        holder.mItemBinding.appVpnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutAppListItemBinding mItemBinding;

        public ViewHolder(@NonNull View itemView, LayoutAppListItemBinding itemBinding) {
            super(itemView);
            mItemBinding = itemBinding;
        }

        public void bindData(AppList item) {
            mItemBinding.setAppList(item);
        }
    }
}
