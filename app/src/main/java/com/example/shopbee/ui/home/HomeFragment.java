package com.example.shopbee.ui.home;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.dingmouren.layoutmanagergroup.banner.BannerLayoutManager;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.databinding.HomeBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.home.adapter.BannerAdapter;
import com.example.shopbee.ui.home.adapter.DealAdapter;
import com.example.shopbee.ui.base.BaseFragment;
import com.example.shopbee.utils.ColorUtils;
import com.example.shopbee.utils.NetworkUtils;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment<HomeBinding, HomeViewModel> implements HomeNavigator {
    HomeBinding binding;
    DealAdapter dealAdapter;

    final int maxScrollHeight = 200;
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
        setUpToolbar();
        syncData();
        observeData();
        setUpRecyclerView();
    }

    void setUpToolbar() {
        binding.toolbarLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = binding.scrollView.getScrollY();
                float percentage = Math.min(1, (float)scrollY / (float) maxScrollHeight);
                binding.toolbarLayout.setBackgroundColor(ColorUtils.adjustAlpha(Color.WHITE, percentage));
                int bagIconColor = ColorUtils.interpolateColor(Color.WHITE, Color.BLACK, percentage);
                binding.bagIcon.setColorFilter(bagIconColor, PorterDuff.Mode.SRC_IN);
                int messageIconColor = ColorUtils.interpolateColor(Color.WHITE, Color.BLACK, percentage);
                binding.messageIcon.setColorFilter(messageIconColor, PorterDuff.Mode.SRC_IN);
            }
        });
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
        // banner recycler view
        BannerLayoutManager bannerLayoutManager = new BannerLayoutManager(requireContext(), binding.bannerRCV, 4, OrientationHelper.HORIZONTAL);
        bannerLayoutManager.setTimeSmooth(200f);
        binding.bannerRCV.setLayoutManager(bannerLayoutManager);
        binding.bannerRCV.setAdapter(new BannerAdapter());
        // deals recycler view
        binding.dealRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dealAdapter = new DealAdapter(new ArrayList<AmazonDealsResponse.Data.Deal>());
        binding.dealRCV.setAdapter(dealAdapter);
    }
}
