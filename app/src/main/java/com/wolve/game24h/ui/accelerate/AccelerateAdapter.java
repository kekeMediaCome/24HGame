package com.wolve.game24h.ui.accelerate;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wolve.libcommon.view.VNTextView;
import com.wolve.game24h.R;
import com.wolve.game24h.ui.accelerate.dummy.DummyContent;

import java.util.List;

public class AccelerateAdapter extends RecyclerView.Adapter<AccelerateAdapter.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;

    public AccelerateAdapter(List<DummyContent.DummyItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_accelerate_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mAppName.setText(mValues.get(position).id);
//        holder.mAppUseTime.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAppName;
        public final VNTextView mAppUseTime;
        public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAppName = (TextView) view.findViewById(R.id.app_name);
            mAppUseTime = (VNTextView) view.findViewById(R.id.app_use_time);
        }
    }
}