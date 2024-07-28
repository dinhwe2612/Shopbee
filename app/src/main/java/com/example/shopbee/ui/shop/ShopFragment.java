package com.example.shopbee.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ShopBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.toolbar.ToolbarView;
import com.example.shopbee.ui.base.BaseFragment;

public class ShopFragment extends BaseFragment<ShopBinding, ShopViewModel> implements ShopNavigator,ToolbarView.NavigateUpClickListener, ToolbarView.SearchClickListener {
    ToolbarView toolbarView;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shop;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getViewDataBinding().topBar.addView(toolbarView.getRootView());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolbarView = new ToolbarView(inflater, container);
        toolbarView.setTitle("");
        toolbarView.enableNavigateUp(this);
        toolbarView.setSearchClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onNavigateUpClick() {

    }

    @Override
    public void onSearchClick() {

    }
}