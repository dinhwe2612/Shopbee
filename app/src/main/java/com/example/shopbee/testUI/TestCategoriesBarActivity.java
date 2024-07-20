package com.example.shopbee.testUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.impl.categoriesbar.NonScrollableRecyclerView;
import com.example.shopbee.testUI.CategoriesBarAdapter.CategoriesBarAdapter;

public class TestCategoriesBarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_bar);
        NonScrollableRecyclerView recyclerView = this.findViewById(R.id.categoryList);
        View animatedLine = this.findViewById(R.id.animatedLine);
        CategoriesBarAdapter adapter = new CategoriesBarAdapter(animatedLine);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter.setViewX(recyclerView);
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
    }
}