package com.example.shopbee.ui.profile.myorder;

import android.app.Dialog;
import android.content.Intent;
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
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
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
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.changeCountry.changeCountryDialog;
import com.example.shopbee.ui.home.HomeFragment;
import com.example.shopbee.ui.profile.adapter.OrderDetailProductAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyOrderDetailFragment extends BaseFragment<OrderDetailsBinding, MyOrderDetailViewModel> implements MyOrderDetailNavigator, OrderDetailProductAdapter.Listener, DialogsManager.Listener {
    @Inject
    DialogsManager dialogsManager;
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

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.SELECT_PROFILE_ICON;
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
        orderDetailProductAdapter = new OrderDetailProductAdapter(orderResponse.getOrder_detail(), this);
        recyclerView.setAdapter(orderDetailProductAdapter);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMyOrder();
            }
        });

        binding.reorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "delivered":
                    case "cancelled":
                        orderResponse.setOrder_number(generateUniqueOrderNumber());
                        orderResponse.setTracking_number(generateUniqueTrackingNumber());
                        reorder(orderResponse);
                        break;
                    case "processing":
                        cancelAnOrder(v);
                        break;
                }
            }
        });
        binding.leaveFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (status){
                    case "delivered":
                        navigateToFeedbackPage();
//                        dialogsManager.showWriteReviewDialog();
                        break;
                    case "cancelled":
                        backToHomeFragment();
                        break;
                    case "processing":
                        backToHomeFragment();
                        break;

                }
            }
        });
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

    @Override
    public void navigateToFeedbackPage() {
        NavController navController = NavHostFragment.findNavController(this);

        NavOptions navOptions = new NavOptions.Builder()
                .setEnterAnim(R.anim.fade_in)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.fade_out)
                .build();

        Bundle bundle = new Bundle();
        bundle.putParcelable("orderResponse", orderResponse);

        navController.navigate(R.id.leaveFeedBackFragment, bundle, navOptions);
    }

    @Override
    public void backToMyOrder() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }

    @Override
    public void reorder(OrderResponse orderResponse) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle(); 
        bundle.putParcelable("orderResponse", orderResponse);
        navController.navigate(R.id.checkoutFragment, bundle);
    }

    @Override
    public void backToHomeFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.homeFragment);
    }

    @Override
    public void onItemClicked(int position) {
        OrderDetailResponse orderDetailResponse = orderResponse.getOrder_detail().get(position);
        dialogsManager.moreVariation(orderDetailResponse);
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

    }
    private void cancelAnOrder(View v){
        PopupDialog.getInstance(v.getContext())
            .standardDialogBuilder()
            .createStandardDialog()
            .setHeading("Cancel an order!")
            .setPositiveButtonBackgroundColor(R.color.dark_red)
            .setPositiveButtonTextColor(R.color.white_light_theme)
            .setDescription("Are you sure you want to cancel?" +
                    " This action cannot be undone")
            .setIcon(R.drawable.cancel)
            .build(new StandardDialogActionListener() {
                @Override
                public void onPositiveButtonClicked(Dialog dialog) {
                    orderResponse.setStatus("cancelled");
                    listOrderResponse.getList_order().get(position).setStatus("cancelled");
                    binding.status.setText("Cancelled");
                    binding.reorder.setText("Reorder");
                    binding.leaveFeedback.setText("Go to shopping");
                    viewModel.getCompositeDisposable().add(viewModel.getRepository().updateOrderFirebase(orderResponse)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(success -> {
                                        if (success) {
                                            PopupDialog.getInstance(dialog.getContext())
                                                .statusDialogBuilder()
                                                .createSuccessDialog()
                                                .setHeading("Done")
                                                .setDescription("You have successfully" +
                                                        " cancelled your order")
                                                .build(Dialog::dismiss)
                                                .show();
                                        } else {

                                        }
                                    },
                                    error -> {

                                    })
                    );
                    dialog.dismiss();
                }
                @Override
                public void onNegativeButtonClicked(Dialog dialog) {
                    dialog.dismiss();
                }
            })
            .show();
    }
    private String generateUniqueOrderNumber() {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder builder = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            int characterIndex = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(characterIndex));
        }
        return builder.toString();
    }
    private String generateUniqueTrackingNumber() {
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return "BEE-" + timestamp + builder.toString();
    }
}
