package com.wolve.libcommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VNWindowInsetsFrameLayout extends FrameLayout {
    public VNWindowInsetsFrameLayout(@NonNull Context context) {
        super(context);
    }

    public VNWindowInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VNWindowInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public VNWindowInsetsFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        requestApplyInsets();
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        WindowInsets windowInsets = super.dispatchApplyWindowInsets(insets);
        if (!insets.isConsumed()) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                windowInsets = getChildAt(i).dispatchApplyWindowInsets(insets);
            }
        }
        return windowInsets;
    }
}
