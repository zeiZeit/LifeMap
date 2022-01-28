package com.gyso.timeline;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.gyso.treeview.BuildConfig;

public class TimeLineContainer extends ViewGroup {
    private static final String TAG = TimeLineContainer.class.getSimpleName();
    private static final boolean isDebug = BuildConfig.isDebug;
    public static final Object IS_EDIT_DRAGGING = new Object();
    public static final double DRAG_HIT_SLOP = 60;
    public static final float Z_NOR = 10f;
    public static final float Z_SELECT = 20f;
    public static final int DEFAULT_FOCUS_DURATION = 300;
    public static final float DEFAULT_REMOVE_ANIMATOR_DES = 100;

    public TimeLineContainer(Context context) {
        super(context);
    }

    public TimeLineContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeLineContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TimeLineContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
