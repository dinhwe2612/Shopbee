package com.example.shopbee.impl.categoriesbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.recyclerview.widget.RecyclerView;

public class NonScrollableRecyclerView extends RecyclerView {
    public NonScrollableRecyclerView(Context context) {
        super(context);
    }

    public NonScrollableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NonScrollableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // Handle touch events but do not scroll
        return e.getAction() == MotionEvent.ACTION_MOVE || super.onTouchEvent(e);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        // Do nothing to disable actual scrolling
    }
}
