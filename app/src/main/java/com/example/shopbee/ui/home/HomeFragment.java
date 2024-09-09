package com.example.shopbee.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.dingmouren.layoutmanagergroup.banner.BannerLayoutManager;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.databinding.HomeBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.twooptiondialog.TwoOptionEvent;
import com.example.shopbee.ui.home.adapter.BannerAdapter;
import com.example.shopbee.ui.home.adapter.BestSellerAdapter;
import com.example.shopbee.ui.home.adapter.CategoryAdapter;
import com.example.shopbee.ui.home.adapter.DealAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.utils.ColorUtils;
import com.example.shopbee.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class HomeFragment extends BaseFragment<HomeBinding, HomeViewModel> implements
        HomeNavigator,
        DialogsManager.Listener,
        CategoryAdapter.Listener,
        DealAdapter.Listener,
        BestSellerAdapter.Listener{
    HomeBinding binding;
    DealAdapter dealAdapter;
    BestSellerAdapter bestSellerAdapter;
    DealAdapter recommendedAdapter;
    @Inject
    DialogsManager dialogsManager;
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
    public FragmentType getFragmentType() {
        return FragmentType.SELECT_HOME_ICON;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        binding.searchLayout.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.userSearchFragment);
        });
        setOnClick();
        setUpToolbar();
        syncData();
        observeData();
        setUpRecyclerView();
        return binding.getRoot();
    }

    void setOnClick() {
        binding.myOrder.setOnClickListener(v -> navigateToMyOrder());
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
        HashMap<String, String> query = new HashMap<>();
        query.put("category", "software");
        viewModel.syncBestSellerProducts(query);
        viewModel.syncRecommendedProducts(NetworkUtils.createAmazonDealsQuery());
    }
    void observeData() {
        viewModel.getDealProducts().observe(getViewLifecycleOwner(), deals -> {
            dealAdapter.setProducts(deals);
        });
        viewModel.getBestSellerProducts().observe(getViewLifecycleOwner(), bestSellers -> {
            bestSellerAdapter.setProducts(bestSellers);
        });
        viewModel.getRecommendedProducts().observe(getViewLifecycleOwner(), deals -> {
            recommendedAdapter.setProducts(deals);
        });
    }
    void setUpRecyclerView() {
        // banner recycler view
        BannerLayoutManager bannerLayoutManager = new BannerLayoutManager(requireContext(), binding.bannerRCV, 4, OrientationHelper.HORIZONTAL);
        bannerLayoutManager.setTimeSmooth(200f);
        binding.bannerRCV.setLayoutManager(bannerLayoutManager);
        binding.bannerRCV.setAdapter(new BannerAdapter());
        // category recycler view
        binding.categoriesRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoriesRCV.setAdapter(new CategoryAdapter(this));
        // deals recycler view
        binding.dealRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        dealAdapter = new DealAdapter(this);
        binding.dealRCV.setAdapter(dealAdapter);
        // best seller recycler view
        binding.bestSellerRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bestSellerAdapter = new BestSellerAdapter(this);
        binding.bestSellerRCV.setAdapter(bestSellerAdapter);
        // recommended recycler view
        binding.recommendedRCV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recommendedAdapter = new DealAdapter(this);
        binding.recommendedRCV.setAdapter(recommendedAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        dialogsManager.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        dialogsManager.unregisterListener(this);
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof TwoOptionEvent) {
            Toast.makeText(getContext(), "TwoOptionEvent " + ((TwoOptionEvent) event).toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCategoryClick(String categoryId) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putString("category", categoryId);
        navController.navigate(R.id.searchFragment, bundle);
    }

    @Override
    public void navigateToMyOrder() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.myOrderFragment);
    }

    @Override
    public void navigateToProductDetail(String asin) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putString("asin", asin);
        navController.navigate(R.id.productDetailFragment, bundle);
    }

    @Override
    public void onDealItemClick(String asin) {
        navigateToProductDetail(asin);
    }

    @Override
    public void onBestSellerItemClick(String asin) {
        navigateToProductDetail(asin);
    }
}
