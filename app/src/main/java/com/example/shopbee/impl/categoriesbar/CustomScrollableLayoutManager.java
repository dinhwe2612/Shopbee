package com.example.shopbee.impl.categoriesbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomScrollableLayoutManager extends LinearLayoutManager {
    private boolean scrollBackward = false;

    public boolean isScrollingBackward() {
        return scrollBackward;
    }

    public CustomScrollableLayoutManager(Context context) {
        super(context);
    }

    public CustomScrollableLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CustomScrollableLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        Log.d("CustomScrollableLayoutManager", "scrolled: " + dx);
        if (dx < 0) scrollBackward = true;
        else if (dx > 0) scrollBackward = false;
        return scrolled;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollVerticallyBy(dy, recycler, state);
        return scrolled;
    }

}
