package com.example.shopbee.ui.bag;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.databinding.BagBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.toolbar.ToolbarView;
import com.example.shopbee.ui.bag.adapter.BagAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.promoCode.PromoCodeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class BagFragment extends BaseFragment<BagBinding, BagViewModel> implements BagNavigator, ToolbarView.SearchClickListener, DialogsManager.Listener, View.OnClickListener {
    @Inject
    DialogsManager dialogsManager;
    MutableLiveData<PromoCodeResponse> promoCodeResponse = new MutableLiveData<>();
    BagAdapter bagAdapter = new BagAdapter();
    ToolbarView toolbarView;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bag;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getViewDataBinding().topBar.addView(toolbarView.getRootView());
        viewModel.syncBagLists();
        getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        getViewDataBinding().recyclerView.setAdapter(bagAdapter);
        viewModel.getBagProducts().observe(getViewLifecycleOwner(), products -> {
            bagAdapter.setQuantities(viewModel.getBagQuantities().getValue());
            bagAdapter.setVariations(viewModel.getBagVariations().getValue());
            bagAdapter.setProducts(products);
            getViewDataBinding().priceTotal.setText(getPriceTotal() + "$");
            getViewDataBinding().discountTotal.setText("-" + 0 + "$");
            getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
            getViewDataBinding().promoCodeText.setText("");
        });
        viewModel.getInProgress().observe(getViewLifecycleOwner(), inProgress -> {
            if (inProgress) {
                getViewDataBinding().loading.setVisibility(View.VISIBLE);
                animateLoading();
//                showProgressDialog();
            } else {
                stopLoadingAnimations();
                getViewDataBinding().loading.setVisibility(View.GONE);
                getViewDataBinding().recyclerView.setVisibility(View.VISIBLE);
//                hideProgressDialog();
            }
        });
        getViewDataBinding().promoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.syncPromoCodes();
                viewModel.getPromoCodes().observe(getViewLifecycleOwner(), result -> {
                    PromoCodeDialog promoCodeDialog = PromoCodeDialog.newInstance(dialogsManager);
                    promoCodeDialog.setPromoCodeResponseList(result);
                    promoCodeDialog.setPromoCodeResponse(promoCodeResponse.getValue());
                    promoCodeDialog.setOnCollectVoucherListener(new PromoCodeDialog.onCollectVoucherListener() {
                        @Override
                        public void onCollectVoucher() {
                            // navigate to collect voucher fragment
                        }
                    });
                });
            }
        });
        promoCodeResponse.observe(getViewLifecycleOwner(), updatedPromoCode ->{
            if (updatedPromoCode != null) {
                getViewDataBinding().promoCodeText.setText(updatedPromoCode.getCode());
                getViewDataBinding().discountTotal.setText("-" + updatedPromoCode.processDiscount(getPriceTotal()) + "$");
                getViewDataBinding().afterDiscountTotal.setText(updatedPromoCode.processPrice(getPriceTotal()) + "$");
            }
            else {
                getViewDataBinding().promoCodeText.setText("");
                getViewDataBinding().discountTotal.setText("-" + 0 + "$");
                getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
            }
        });
        getViewDataBinding().checkOut.setOnClickListener(this);
    }
    public void animateLoading() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) getViewDataBinding().loading1.getBackground();
        if (!animationDrawable1.isRunning()) {
            animationDrawable1.start();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) getViewDataBinding().loading2.getBackground();
        if (!animationDrawable2.isRunning()) {
            animationDrawable2.start();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) getViewDataBinding().loading3.getBackground();
        if (!animationDrawable3.isRunning()) {
            animationDrawable3.start();
        }
    }

    public void stopLoadingAnimations() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) getViewDataBinding().loading1.getBackground();
        if (animationDrawable1.isRunning()) {
            animationDrawable1.stop();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) getViewDataBinding().loading2.getBackground();
        if (animationDrawable2.isRunning()) {
            animationDrawable2.stop();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) getViewDataBinding().loading3.getBackground();
        if (animationDrawable3.isRunning()) {
            animationDrawable3.stop();
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolbarView = new ToolbarView(inflater, container);
        toolbarView.setTitle("");
        toolbarView.setSearchClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSearchClick() {

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
        if (event instanceof PromoCodeResponse) {
            promoCodeResponse.setValue((PromoCodeResponse) event);
        }
    }

    public float getPriceTotal() {
        // get total price from String price (may contain $) of each product in list viewModel.getBagProducts().getValue()
        float totalPrice = 0f;
        for (int i = 0; i < viewModel.getBagProducts().getValue().size(); i++) {
            // convert each product's price into float
            String price = viewModel.getBagProducts().getValue().get(i).getData().getProduct_price();
            price = price.replace("$", "");
            totalPrice += Float.parseFloat(price) * viewModel.getBagQuantities().getValue().get(i);
        }
        return totalPrice;
    }
    private String generateUniqueOrderNumber() {
        return "No" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
    private String generateUniqueTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    @Override
    public void onClick(View view) {
        int sum_quantity = 0;
        for (int quantity: viewModel.getBagQuantities().getValue()) {
            sum_quantity += quantity;
        }
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        for (int i = 0; i < viewModel.getBagProducts().getValue().size(); i++) {
            AmazonProductDetailsResponse product = viewModel.getBagProducts().getValue().get(i);
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse(product.getData().getAsin(), product.getData().getProduct_title(), viewModel.getBagQuantities().getValue().get(i), product.getData().getProduct_price(), product.getData().getProduct_photo());
            orderDetailResponseList.add(orderDetailResponse);
        }
        OrderResponse orderResponse;
        if (promoCodeResponse.getValue() == null) {
            orderResponse = new OrderResponse (
                    new Date().toString(),
                    sum_quantity,
                    "processing",
                    generateUniqueOrderNumber(),
                    generateUniqueTrackingNumber(),
                    "",
                    "0$",
                    orderDetailResponseList);
        } else {
            orderResponse = new OrderResponse (
                    new Date().toString(),
                    sum_quantity,
                    "processing",
                    generateUniqueOrderNumber(),
                    generateUniqueTrackingNumber(),
                    "",
                    promoCodeResponse.getValue().processDiscount(getPriceTotal()) + "$",
                    orderDetailResponseList);
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("orderResponse", orderResponse);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.checkoutFragment, bundle);
//        OrderResponse orderResponse1 = bundle.getParcelable("orderResponse");
    }
}
