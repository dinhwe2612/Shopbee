package com.example.shopbee.ui.checkout;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AddressResponse;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.PaymentResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.CheckoutBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;

public class CheckoutFragment extends BaseFragment<CheckoutBinding, CheckoutViewModel> implements CheckoutNavigator{
    private CheckoutBinding binding;
    private UserResponse userResponse;
    private AddressResponse currentAddress;
    private PaymentResponse currentPayment;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        loadRealtimeData();
        setUpShippingAddress();
        setUpPaymentMethod();
        setUpAmount();
        binding.submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) binding.paymentMethodImage.getLayoutParams();
                params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
                params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
                binding.paymentMethodImage.setLayoutParams(params);
                binding.paymentMethodImage.setBackgroundResource(R.drawable.master);
                binding.numberCard.setText("**** **** **** " + currentPayment.getNumber().substring(12, 15));
                break;
        }
        binding.changePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void setUpAmount(){
        binding.orderAmount.setText("112$");
        binding.deliveryAmount.setText("10S");
        binding.discountAmount.setText("-2$");
        binding.totalAmount.setText("120$");
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeShippingAddress() {

    }

    @Override
    public void changePaymentMethod() {

    }

    @Override
    public void submitOrder() {

    }
}
