package com.wolve.libcommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VNCornerFrameLayout extends FrameLayout {
    public VNCornerFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public VNCornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VNCornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public VNCornerFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        VNViewHelper.setViewOutline(this, attrs, defStyleAttr, defStyleRes);
    }

    public void setViewOutline(int radius, int radiusSide) {
        VNViewHelper.setViewOutline(this, radius, radiusSide);
    }
}
