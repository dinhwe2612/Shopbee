package com.example.shopbee.ui.checkout;

import android.app.Dialog;
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
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.CheckoutBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.main.MainActivity;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CheckoutFragment extends BaseFragment<CheckoutBinding, CheckoutViewModel> implements CheckoutNavigator{
    private CheckoutBinding binding;
    private UserResponse userResponse;
    private AddressResponse finalAddress;
    private PaymentResponse finalPayment;
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
        binding = CheckoutBinding.inflate(LayoutInflater.from(getContext()));
        orderResponse = getArguments().getParcelable("orderResponse");
        loadRealtimeData();
        setUpShippingAddress();
        setUpPaymentMethod();
        setUpAmount();
        binding.submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalAddress == null || finalPayment == null){
                    PopupDialog.getInstance(v.getContext())
                            .statusDialogBuilder()
                            .createWarningDialog()
                            .setHeading("Missing")
                            .setDescription("Please select a default address/payment method to move forward.")
                            .build(Dialog::dismiss)
                            .show();
                } else {
                    orderResponse.setStatus("processing");
                    orderResponse.setPayment(finalPayment.getType());
                    orderResponse.setAddress(finalAddress.toString());
                    orderResponse.setDate(DateTimeToFormat());
                    listOrderResponse.getList_order().add(orderResponse);
                    viewModel.getCompositeDisposable().add(viewModel.getRepository().updateOrderFirebase(orderResponse)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(success -> {
                                        if (success) {
                                            viewModel.getRepository().deleteBag();
                                            navigateToSuccessFragment();
                                        } else {
                                            PopupDialog.getInstance(v.getContext())
                                                    .statusDialogBuilder()
                                                    .createWarningDialog()
                                                    .setHeading("Pending!")
                                                    .setDescription("You verification is under" +
                                                            " observation. Try again later.")
                                                    .build(Dialog::dismiss)
                                                    .show();
                                        }
                                    },
                                    error -> {

                                    })
                    );
                }
            }
        });
        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupDialog.getInstance(getContext())
                        .standardDialogBuilder()
                        .createStandardDialog()
                        .setHeading("Postpone Ordering")
                        .setDescription("Promotion is running out soon. Do you want to postpone your order?")
                        .setPositiveButtonText("Continue order")
                        .setNegativeButtonText("Go back")
                        .setPositiveButtonTextColor(R.color.white)
                        .setIcon(R.drawable.sad_icon)
                        .build(new StandardDialogActionListener() {
                            @Override
                            public void onPositiveButtonClicked(Dialog dialog) {
                                dialog.dismiss();
                            }
                            @Override
                            public void onNegativeButtonClicked(Dialog dialog) {
                                backToPreviousFragment();
                                dialog.dismiss();
                            }
                        })
                        .show();
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
        AddressResponse currentAddress = null;
        for (AddressResponse addressResponse : userResponse.getAddress()) {
            if (addressResponse.getDef()){
                currentAddress = addressResponse;
            }
        }
        finalAddress = currentAddress;
        if (currentAddress == null){
            binding.fullName.setText("");
            binding.address.setText("Default address is missing. Tap choose to set your default address.");
            binding.changeAddress.setText("Choose");
        } else {
            binding.fullName.setText(currentAddress.getName());
            binding.address.setText(currentAddress.toString());
            binding.changeAddress.setText("Change");
        }
        binding.changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeShippingAddress();
            }
        });
    }
    private void setUpPaymentMethod(){
        PaymentResponse currentPayment = null;
        for (PaymentResponse paymentResponse : userResponse.getPayment()) {
            if (paymentResponse.getDef()){
                currentPayment = paymentResponse;
            }
        }
        finalPayment = currentPayment;
        if (currentPayment == null){
            binding.isShopbeePay.setVisibility(View.GONE);
            binding.paymentMethodImage.setVisibility(View.GONE);
            binding.paymentLayout.setVisibility(View.GONE);
            binding.numberCard.setText("Default payment method is missing. Tap choose to set your default payment method.");
            binding.changePayment.setText("Choose");
        } else {
            switch (currentPayment.getType()){
                case "shopbee":
                    binding.isShopbeePay.setVisibility(View.VISIBLE);
                    binding.paymentLayout.setVisibility(View.VISIBLE);
                    binding.paymentMethodImage.setBackgroundResource(R.drawable.wallet);
                    break;
                case "visa":
                    binding.isShopbeePay.setVisibility(View.GONE);
                    binding.paymentLayout.setVisibility(View.VISIBLE);
                    binding.paymentMethodImage.setBackgroundResource(R.drawable.visa);
                    binding.numberCard.setText("**** **** **** " + currentPayment.getNumber().substring(12, 16));

                    break;
                case "master":
                    binding.isShopbeePay.setVisibility(View.GONE);
                    binding.paymentLayout.setVisibility(View.VISIBLE);
                    binding.paymentMethodImage.setBackgroundResource(R.drawable.master);
                    binding.numberCard.setText("**** **** **** " + currentPayment.getNumber().substring(12, 16));
                    break;
            }
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
        if (orderResponse.getFreeship().equals("0$")){
            binding.freeshipLayout.setVisibility(View.GONE);
        } else {
            binding.freeshipLayout.setVisibility(View.VISIBLE);
            binding.freeshipAmount.setText("-" + orderResponse.getFreeship());
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
    public void backToPreviousFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }

    @Override
    public void navigateToSuccessFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.successFragment);
    }

    private String DateTimeToFormat(){
        Date now = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDateTime = dateTimeFormat.format(now);
        return formattedDateTime;
    }
}
