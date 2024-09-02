package com.example.shopbee.ui.profile.myorder;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AddressResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.PaymentResponse;
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

import java.util.List;

public class MyOrderDetailFragment extends BaseFragment<OrderDetailsBinding, MyOrderDetailViewModel> implements MyOrderDetailNavigator {
    OrderDetailsBinding binding;
    private UserResponse userResponse;
    private ListOrderResponse listOrderResponse;
    private OrderResponse orderResponse;
    private OrderDetailProductAdapter orderDetailProductAdapter;
    private String status;
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
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        position = getArguments().getInt("position");
        status = getArguments().getString("status");
        loadRealtimeData();
        orderResponse = listOrderResponse.getList_order().get(position);
        binding.orderNumber.setText(orderResponse.getOrder_number());
        binding.date.setText(orderResponse.getDate());
        binding.trackingNumber.setText(orderResponse.getTracking_number());
        binding.quantity.setText(String.valueOf(orderResponse.getQuantity()));
        String newStatus = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        binding.status.setText(newStatus);
        for (AddressResponse addressResponse : userResponse.getAddress()) {
            if (addressResponse.getDef()){
                binding.shippingAddress.setText(addressResponse.toString());
            }
        }
        Log.d("TAG", "onViewCreated: " + orderResponse.getPayment());
        switch (orderResponse.getPayment()){
            case "shopbee":
                binding.isShopbeePay.setVisibility(View.VISIBLE);
                binding.paymentMethodImage.setBackgroundResource(R.drawable.wallet);
                break;
            case "visa":
                binding.isShopbeePay.setVisibility(View.GONE);
                binding.paymentMethodImage.setBackgroundResource(R.drawable.visa);
                for (PaymentResponse paymentResponse : userResponse.getPayment()) {
                    if (paymentResponse.getType().equals("visa")){
                        binding.paymentMethod.setText("**** **** **** " + paymentResponse.getNumber().substring(12, 15));
                    }
                }
                break;
            case "master":
                binding.isShopbeePay.setVisibility(View.GONE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.paymentMethodImage.getLayoutParams();
                params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                binding.paymentMethodImage.setLayoutParams(params);
                binding.paymentMethodImage.setBackgroundResource(R.drawable.master);
                for (PaymentResponse paymentResponse : userResponse.getPayment()) {
                    if (paymentResponse.getType().equals("master")){
                        binding.paymentMethod.setText("**** **** **** " + paymentResponse.getNumber().substring(12, 15));
                    }
                }
                break;
        }
        if (orderResponse.getDiscount().isEmpty()){
            binding.discountLayout.setVisibility(View.GONE);
        }
        else binding.discount.setText("-" + orderResponse.getDiscount());
        binding.totalAmount.setText(orderResponse.getTotal_amount());

        switch (status){
            case "delivered":
                binding.reorder.setText("Reorder");
                binding.leaveFeedback.setText("Leave feedback");
                break;
            case "processing":
                binding.reorder.setText("Cancel");
                binding.leaveFeedback.setText("Go to shopping");
                break;
            case "cancelled":
                binding.reorder.setText("Reorder");
                binding.leaveFeedback.setText("Go to shopping");
                break;
        }
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
