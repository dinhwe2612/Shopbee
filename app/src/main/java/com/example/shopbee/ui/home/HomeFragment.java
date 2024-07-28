package com.example.shopbee.ui.home;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.databinding.HomeBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.adapter.DealAdapter;
import com.example.shopbee.ui.base.BaseFragment;
import com.example.shopbee.utils.NetworkUtils;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<HomeBinding, HomeViewModel> implements HomeNavigator {
    HomeBinding binding;
    DealAdapter dealAdapter;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        syncData();
        observeData();
        setUpRecyclerView();
    }

    void syncData() {
        viewModel.syncDealProducts(NetworkUtils.createAmazonDealsQuery());
    }
    void observeData() {
        viewModel.getDealProducts().observe(getViewLifecycleOwner(), deals -> {
            dealAdapter.setProducts(deals);
        });
    }
    void setUpRecyclerView() {
        binding.dealRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dealAdapter = new DealAdapter(new ArrayList<AmazonDealsResponse.Data.Deal>());
        binding.dealRCV.setAdapter(dealAdapter);
    }
}
