package com.example.shopbee.ui.profile.myorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopbee.R;
import com.example.shopbee.databinding.MyOrdersBinding;
import com.example.shopbee.databinding.OrderDetailsBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.bag.BagFragment;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyOrderDetailFragment extends BaseFragment<OrderDetailsBinding, MyOrderDetailViewModel> implements MyOrderDetailNavigator {
    OrderDetailsBinding binding;
    @Override
    public int getBindingVariable() {return BR.vm;}

    @Override
    public int getLayoutId() {return R.layout.order_details;}

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

    }
}
