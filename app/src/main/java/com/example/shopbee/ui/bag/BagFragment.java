package com.example.shopbee.ui.bag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.BagBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.toolbar.ToolbarView;
import com.example.shopbee.ui.bag.adapter.BagAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;

public class BagFragment extends BaseFragment<BagBinding, BagViewModel> implements BagNavigator, ToolbarView.SearchClickListener {
    BagAdapter bagAdapter = new BagAdapter();
    ToolbarView toolbarView;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bag;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
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
        toolbarView.setSearchClickListener(this);
        getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        getViewDataBinding().recyclerView.setAdapter(bagAdapter);
        viewModel.getBagProducts().observe(getViewLifecycleOwner(), products -> {
            bagAdapter.setQuantities(viewModel.getBagQuantities().getValue());
            bagAdapter.setVariations(viewModel.getBagVariations().getValue());
            bagAdapter.setProducts(products);
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSearchClick() {

    }
}
