package com.example.shopbee.ui.profile.myorder;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.MyOrdersBinding;
import com.example.shopbee.databinding.OrderDetailsBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.bag.BagFragment;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.changeCountry.changeCountryDialog;
import com.example.shopbee.ui.profile.adapter.OrderDetailProductAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MyOrderDetailFragment extends BaseFragment<OrderDetailsBinding, MyOrderDetailViewModel> implements MyOrderDetailNavigator {
    OrderDetailsBinding binding;
    private UserResponse userResponse;
    private ListOrderResponse listOrderResponse;
    private OrderResponse orderResponse;
    private OrderDetailProductAdapter orderDetailProductAdapter;
    private int position;
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
        loadRealtimeData();
        position = getArguments().getInt("position");

        orderResponse = listOrderResponse.getList_order().get(position);
        binding.orderNumber.setText(orderResponse.getOrder_number());
        binding.date.setText(orderResponse.getDate());
        binding.trackingNumber.setText(orderResponse.getTracking_number());
        binding.quantity.setText(String.valueOf(orderResponse.getQuantity()));
        String status = orderResponse.getStatus();
        String newStatus = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        binding.status.setText(newStatus);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderDetailProductAdapter = new OrderDetailProductAdapter(orderResponse.getOrder_detail());
        recyclerView.setAdapter(orderDetailProductAdapter);
    }
    void loadRealtimeData(){
        viewModel.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
               userResponse = response;
            }
        });
        viewModel.getOrderResponse().observeForever(new Observer<ListOrderResponse>() {
            @Override
            public void onChanged(ListOrderResponse responses) {
                listOrderResponse = responses;
            }
        });
    }
}
