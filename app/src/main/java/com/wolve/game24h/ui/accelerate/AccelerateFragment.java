package com.wolve.game24h.ui.accelerate;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolve.libnavannotation.FragmentDestination;
import com.wolve.game24h.R;
import com.wolve.game24h.ui.accelerate.dummy.DummyContent;

@FragmentDestination(pageUrl = "main/tabs/aclerate", asStarter = true)
public class AccelerateFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private AccelerateAdapter accelerateAdapter;

    public AccelerateFragment() {

    }

    public static AccelerateFragment newInstance(int columnCount) {
        AccelerateFragment fragment = new AccelerateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accelerate, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new AccelerateAdapter(DummyContent.ITEMS));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}