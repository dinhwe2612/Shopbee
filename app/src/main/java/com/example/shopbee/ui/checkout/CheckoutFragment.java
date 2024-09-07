package com.example.shopbee.ui.checkout;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AddressResponse;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.PaymentResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.CheckoutBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.main.MainActivity;

public class CheckoutFragment extends BaseFragment<CheckoutBinding, CheckoutViewModel> implements CheckoutNavigator{
    private CheckoutBinding binding;
    private UserResponse userResponse;
    private AddressResponse currentAddress;
    private PaymentResponse currentPayment;
    private OrderResponse orderResponse;
    private ListOrderResponse listOrderResponse;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.checkout;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        orderResponse = getArguments().getParcelable("orderResponse");
        loadRealtimeData();
        setUpShippingAddress();
        setUpPaymentMethod();
        setUpAmount();
        binding.submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitOrder();
            }
        });
        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPreviousFragment();
            }
        });
        return binding.getRoot();
    }
    public void loadRealtimeData(){
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
    private void setUpShippingAddress(){
        for (AddressResponse addressResponse : userResponse.getAddress()) {
            if (addressResponse.getDef()){
                currentAddress = addressResponse;
            }
        }
        binding.fullName.setText(userResponse.getFull_name());
        binding.address.setText(currentAddress.toString());
        binding.changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeShippingAddress();
            }
        });
    }
    private void setUpPaymentMethod(){
        for (PaymentResponse paymentResponse : userResponse.getPayment()) {
            if (paymentResponse.getDef()){
                currentPayment = paymentResponse;
            }
        }
        switch (currentPayment.getType()){
            case "shopbee":
                binding.isShopbeePay.setVisibility(View.VISIBLE);
                binding.paymentMethodImage.setBackgroundResource(R.drawable.wallet);
                break;
            case "visa":
                binding.isShopbeePay.setVisibility(View.GONE);
                binding.paymentMethodImage.setBackgroundResource(R.drawable.visa);
                binding.numberCard.setText("**** **** **** " + currentPayment.getNumber().substring(12, 15));

                break;
            case "master":
                binding.isShopbeePay.setVisibility(View.GONE);
                /*RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.paymentMethodImage.getLayoutParams();
                params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                binding.paymentMethodImage.setLayoutParams(params);*/
                binding.paymentMethodImage.setBackgroundResource(R.drawable.master);
                binding.numberCard.setText("**** **** **** " + currentPayment.getNumber().substring(12, 15));
                break;
        }
        binding.changePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePaymentMethod();
            }
        });
    }
    private void setUpAmount(){
        binding.orderAmount.setText(orderResponse.getOrderPrice());
        binding.deliveryAmount.setText("10$");
        if (orderResponse.getDiscount().equals("0$")){
            binding.discountLayout.setVisibility(View.GONE);
        } else {
            binding.discountLayout.setVisibility(View.VISIBLE);
            binding.discountAmount.setText("-" + orderResponse.getDiscount());
        }
        binding.totalAmount.setText(orderResponse.getTotal_amount());
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeShippingAddress() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.shippingFragment);
    }
    @Override
    public void changePaymentMethod() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.paymentFragment);
    }
    @Override
    public void submitOrder() {
        orderResponse.setPayment(currentPayment.getType());
        listOrderResponse.getList_order().add(orderResponse);
        for (OrderResponse order : listOrderResponse.getList_order()) {
            Log.d("TAG", "submitOrder: " + order.getOrder_number());
        }
        viewModel.updateOrderFirebase(orderResponse);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.successFragment);
    }

    @Override
    public void backToPreviousFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }
}
