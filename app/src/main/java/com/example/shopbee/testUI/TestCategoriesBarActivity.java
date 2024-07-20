package com.example.shopbee.testUI;

import android.os.Bundle;
import android.view.View;

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
    }
}