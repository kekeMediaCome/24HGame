package com.wolve.libcommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class VNCornerLinearLayout extends LinearLayout {
    public VNCornerLinearLayout(Context context) {
        this(context, null);
    }

    public VNCornerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VNCornerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public VNCornerLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        VNViewHelper.setViewOutline(this, attrs, defStyleAttr, defStyleRes);
    }
}
