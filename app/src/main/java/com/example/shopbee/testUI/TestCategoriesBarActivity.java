package com.example.shopbee.testUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.shopbee.R;
import com.example.shopbee.impl.categoriesbar.CustomScrollableLayoutManager;
import com.example.shopbee.impl.categoriesbar.CustomScrollableRecyclerView;
import com.example.shopbee.testUI.CategoriesBarAdapter.CategoriesBarAdapter;

public class TestCategoriesBarActivity extends AppCompatActivity {
    private int getSnappedPosition(RecyclerView recyclerView, SnapHelper snapHelper) {
        View snappedView = snapHelper.findSnapView(recyclerView.getLayoutManager());
        if (snappedView == null) return RecyclerView.NO_POSITION;
        return recyclerView.getLayoutManager().getPosition(snappedView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_bar);
        CustomScrollableRecyclerView recyclerView = this.findViewById(R.id.categoryList);
        View animatedLine = this.findViewById(R.id.animatedLine);
        CategoriesBarAdapter adapter = new CategoriesBarAdapter(animatedLine);
        recyclerView.setAdapter(adapter);
        CustomScrollableLayoutManager layoutManager = new CustomScrollableLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setViewX(recyclerView);
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

//        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(adapter);
//        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
////                adapter.animateAfterLayout();
//            }
//        });
//        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                // Ensure the listener is removed after the first call
//                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//                // Find the specific item view within the RecyclerView
//                View itemView = recyclerView.getLayoutManager().findViewByPosition(3);
//                if (itemView != null) {
//                    int left = itemView.getLeft();
//                    float x = itemView.getX();
//                    // Now you can use the 'left' and 'x' values as needed
//                    Log.d("RecyclerViewItem", "Left: " + left + ", X: " + x);
//                }
//            }
//        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int oldFirstVisibleItemPosition = 0;
            int itemsScrolledThrough = 0;
            boolean scrollBack;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (layoutManager.findFirstVisibleItemPosition() > adapter.getImpl().position || layoutManager.findLastVisibleItemPosition() < adapter.getImpl().position) {
                    animatedLine.setVisibility(View.INVISIBLE);
                    adapter.notifyItemChanged(adapter.getImpl().position);
                }
                else {
                    adapter.notifyItemChanged(adapter.getImpl().position);
                    animatedLine.setVisibility(View.VISIBLE);
                }
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
////                    animatedLine.setVisibility(View.INVISIBLE);
//                }
//                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
//                    animatedLine.setVisibility(View.VISIBLE);
//                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    animatedLine.setVisibility(View.VISIBLE);
//                    int newFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                    itemsScrolledThrough = newFirstVisibleItemPosition - oldFirstVisibleItemPosition;
//                    oldFirstVisibleItemPosition = newFirstVisibleItemPosition;
//                    recyclerView.removeOnScrollListener(this);
////                    int position = getSnappedPosition(recyclerView, snapHelper);
//                    int position;
////                    Log.d("RecyclerViewItem", "scrollBack: " + scrollBack);
//                    Log.d("itemsScrolledThrough", "itemsScrolledThrough: " + itemsScrolledThrough);
//                    Log.d("isScrollingBackward", "isScrollingBackward: " + layoutManager.isScrollingBackward());
//                    if (layoutManager.isScrollingBackward()) {
////                        position = adapter.getImpl().position - Math.max(1, Math.abs(itemsScrolledThrough));
////                        if (position < 0) position = 0;
//                        position = adapter.getImpl().position - 1;
//                        if (position < 0) position = 0;
//                        Log.d("RecyclerViewItem",  "findFirstVisibleItemPosition: " + layoutManager.findFirstVisibleItemPosition() + "findLastVisibleItemPosition: " + layoutManager.findLastVisibleItemPosition() + "Position: " + position);
//                        if (position < layoutManager.findFirstVisibleItemPosition()) position = layoutManager.findFirstVisibleItemPosition();
//                        if (position > layoutManager.findLastVisibleItemPosition()) position = layoutManager.findLastVisibleItemPosition();
////
//
//                    }
//                    else {
//                        position = adapter.getImpl().position + 1;
//                        if (position >= adapter.getItemCount()) position = adapter.getItemCount() - 1;
////
//                        Log.d("RecyclerViewItem",  "findFirstVisibleItemPosition: " + layoutManager.findFirstVisibleItemPosition() + "findLastVisibleItemPosition: " + layoutManager.findLastVisibleItemPosition() + "Position: " + position);                        if (position < layoutManager.findFirstVisibleItemPosition()) position = layoutManager.findFirstVisibleItemPosition();
//                        if (position < layoutManager.findFirstVisibleItemPosition()) position = layoutManager.findFirstVisibleItemPosition();
////                        position = adapter.getImpl().position + Math.max(1, Math.abs(itemsScrolledThrough));
////                        if (position >= adapter.getItemCount()) position = adapter.getItemCount() - 1;
//                    }
//                    if (position != RecyclerView.NO_POSITION) {
//                        // Update the selected position here
//                        // For example, you can update a variable or notify an adapter
//                        adapter.notifyItemChanged(adapter.getImpl().position);
//                        adapter.getImpl().position = position;
//                        adapter.notifyItemChanged(adapter.getImpl().position);
//                    }
//                    recyclerView.addOnScrollListener(this);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dx != 0) {
                    animatedLine.setVisibility(View.GONE);
//                    adapter.notifyItemChanged(adapter.getImpl().position);
                }
//                Log.d("RecyclerViewItem", "dx: " + dx + ", dy: " + dy); // Log dx and dy values
                if (dx > 0) {
                    scrollBack = false;
                } else if (dx < 0) {
                    scrollBack = true;
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }
}