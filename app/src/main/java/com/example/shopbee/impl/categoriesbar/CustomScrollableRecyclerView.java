package com.example.shopbee.impl.categoriesbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.testUI.CategoriesBarAdapter.CategoriesBarAdapter;

public class CustomScrollableRecyclerView extends RecyclerView implements CategoriesBarAdapter.getViewX {
    public CustomScrollableRecyclerView(Context context) {
        super(context);
    }

    public CustomScrollableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        // Handle touch events but do not scroll
//        return e.getAction() == MotionEvent.ACTION_MOVE || super.onTouchEvent(e);
//    }
//
//    @Override
//    public void onScrolled(int dx, int dy) {
//        // Do nothing to disable actual scrolling
//    }

    @Override
    public float getViewX(int position) {
        Log.d("TAG", "getViewX: "+getLayoutManager().findViewByPosition(position).getLeft());
        return getLayoutManager().findViewByPosition(position).getLeft();
    }
}
