package com.example.shopbee.testUI;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.shopbee.R;
import com.example.shopbee.testUI.FavoriteAdapter.FavoriteAdapter;
import com.example.shopbee.testUI.FavoriteAdapter.TagAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class TestFavoriteActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private RecyclerView recyclerViewFavorite;
    private RecyclerView recyclerViewTag;
    private FavoriteAdapter favoriteAdapter;
    private TagAdapter tagAdapter;

    private Menu collapsedMenu;
    private  boolean appBarExpended = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_copy);

        final Toolbar toolbar = findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout = findViewById(R.id.appbar);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Favorite");

        recyclerViewTag = findViewById(R.id.recycler_tag);
        recyclerViewFavorite = findViewById(R.id.scrollableview);

        tagAdapter = new TagAdapter();
        favoriteAdapter = new FavoriteAdapter();
        recyclerViewTag.setAdapter(tagAdapter);
        recyclerViewFavorite.setAdapter(favoriteAdapter);
        recyclerViewTag.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(this));

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 50){
                    appBarExpended = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpended = true;
                    invalidateOptionsMenu();
                }
            }
        });
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        if (collapsedMenu != null && (!appBarExpended || collapsedMenu.size() != 1)){
            collapsedMenu.add("Add")
                    .setIcon(R.drawable.grid_view_icon)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        else {

        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        collapsedMenu = menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        if (item.getTitle() == "Add") {
            Toast.makeText(this, "clicked add", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
