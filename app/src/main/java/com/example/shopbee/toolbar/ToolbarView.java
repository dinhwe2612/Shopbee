package com.example.shopbee.toolbar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.ViewToolbarBinding;

//ToolbarView toolbarView = new ToolbarView(LayoutInflater.from(this), findViewById(R.id.toolbar));
//toolbarView.setTitle("ShopBee");
//toolbarView.attachToParent();
public class ToolbarView {
    public interface SearchClickListener {
        void onSearchClick();
    }
    public interface NavigateUpClickListener {
        void onNavigateUpClick();
    }
    SearchClickListener searchClickListener;

    NavigateUpClickListener listener;
    public String title;
    ViewToolbarBinding binding;
    View rootView;
    ViewGroup parent;
    boolean backButtonVisible = false;
    public ToolbarView(LayoutInflater inflater, ViewGroup parent) {
        this.parent = parent;
        rootView = inflater.inflate(R.layout.view_toolbar, parent, false);
        binding = ViewToolbarBinding.bind(rootView);
    }
    public View getRootView() {
        return rootView;
    }
    public void setTitle(String title) {
        this.title = title;
        binding.txtToolbarTitle.setText(title);
    }
    public void onNavigateUpClick() {
        if (this.listener != null) {
            this.listener.onNavigateUpClick();
        }
    }
    public void onSearchClick() {
        if (this.searchClickListener != null) {
            this.searchClickListener.onSearchClick();
        }
    }
    public void setSearchClickListener(SearchClickListener listener) {
        if (this.searchClickListener != null) {
            throw new RuntimeException("SearchClickListener is already set.");
        }
        this.searchClickListener = listener;
        binding.btnSearch.setVisibility(VISIBLE);
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchClick();
            }
        });
    }
    public void enableNavigateUp(NavigateUpClickListener listener) {
        if (this.listener != null) {
            throw new RuntimeException("NavigateUpClickListener is already enabled.");
        }
        this.listener = listener;
        binding.btnBack.setVisibility(View.VISIBLE);
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNavigateUpClick();
            }
        });
    }
    public int isBackButtonVisible() {
        return backButtonVisible ? VISIBLE : GONE;
    }
    public void attachToParent() {
        parent.addView(rootView);
    }
}
