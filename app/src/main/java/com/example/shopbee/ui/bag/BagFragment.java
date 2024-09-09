package com.example.shopbee.ui.bag;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.data.Repository;
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
import com.example.shopbee.ui.common.dialogs.morevariation.moreVariationDialog;
import com.example.shopbee.ui.common.dialogs.promoCode.PromoCodeDialog;
import com.example.shopbee.ui.login.LoginActivity;
import com.example.shopbee.ui.main.MainActivity;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BagFragment extends BaseFragment<BagBinding, BagViewModel> implements BagNavigator, ToolbarView.SearchClickListener, DialogsManager.Listener, View.OnClickListener, BagAdapter.onChangeQuantityListener {
    // handle blank bag
    @Inject
    DialogsManager dialogsManager;
    MutableLiveData<PromoCodeResponse> promoCodeResponse = new MutableLiveData<>();
    BagAdapter bagAdapter = new BagAdapter();
    ToolbarView toolbarView;

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.SELECT_BAG_ICON;
    }

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
        if (viewModel.getRepository().getUserResponse() != null) {
//            displayOptionsForBag(View.GONE, View.GONE);
            getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            getViewDataBinding().recyclerView.setAdapter(bagAdapter);
            bagAdapter.setOnChangeQuantityListener(this);
            observePromoCodes();
            viewModel.getBagProducts().observe(getViewLifecycleOwner(), products -> {
                changeDatasetForAdapter(products);
                Log.d("setPriceUI", "products: " + products.toString() + " " + products.size() + " " + !products.isEmpty());
                if (!products.isEmpty()) {
                    Log.d("setPriceUI", "product if: " + products + " " + products.size() + " " + !products.isEmpty());
                    displayOptionsForBag(View.VISIBLE, View.GONE);
                    Log.d("setPriceUI", "product if 2: " + products + " " + products.size() + " " + !products.isEmpty());

                    Log.d("setPriceUI", "price total before discount: ");

                    setPriceUI();
//                    getViewDataBinding().priceTotal.setText(getPriceTotal() + "$");
//                    if (promoCodeResponse.getValue() != null) {
//                        getViewDataBinding().promoCodeText.setText("");
//                        getViewDataBinding().discountTotal.setText("-" + 0 + "$");
//                        getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
//                    } else {
//                        getViewDataBinding().discountTotal.setText("-" + 0 + "$");
//                        getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
//                        getViewDataBinding().promoCodeText.setText("");
//                    }
                }
                else {
                    Log.d("setPriceUI", "product else: " + products + " " + products.size() + " " + !products.isEmpty());
                    displayOptionsForBag(View.GONE, View.VISIBLE);
                }
            });
            viewModel.getInProgress().observe(getViewLifecycleOwner(), inProgress -> {
                if (inProgress) {
//                    displayOptionsForBag(View.GONE, View.GONE);
                    getViewDataBinding().loading.setVisibility(View.VISIBLE);
                    animateLoading();
//                showProgressDialog();
                } else {
//                    displayOptionsForBag(View.VISIBLE, View.GONE);
                    stopLoadingAnimations();
                    getViewDataBinding().loading.setVisibility(View.GONE);
                    getViewDataBinding().recyclerView.setVisibility(View.VISIBLE);
//                hideProgressDialog();
                }
            });
            viewModel.syncBagProducts();
            viewModel.getPromoCodes().observe(getViewLifecycleOwner(), result -> {
                Log.d("result", "open promoCodeDialog");
                PromoCodeDialog promoCodeDialog = PromoCodeDialog.newInstance(dialogsManager);
                promoCodeDialog.setPromoCodeResponseList(result);
                promoCodeDialog.setPromoCodeResponse(promoCodeResponse.getValue());
                promoCodeDialog.setOnCollectVoucherListener(new PromoCodeDialog.onCollectVoucherListener() {
                    @Override
                    public void onCollectVoucher() {
                        // navigate to collect voucher fragment
                    }
                });
                promoCodeDialog.show(requireActivity().getSupportFragmentManager(), promoCodeDialog.getTag());
            });
            getViewDataBinding().promoCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.syncPromoCodes();
                }
            });

            getViewDataBinding().checkOut.setOnClickListener(this);
        }
        else {
            dealWithNullUser();
        }
    }
    public void changeDatasetForAdapter(List<OrderDetailResponse> orderDetailResponseList) {
        bagAdapter.setProducts(orderDetailResponseList);
        bagAdapter.notifyDataSetChanged();
    }
    public void dealWithNullUser() {
        getViewDataBinding().signIn.setVisibility(View.VISIBLE);
        getViewDataBinding().signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    public void observePromoCodes() {
        promoCodeResponse.observe(getViewLifecycleOwner(), updatedPromoCode -> {
            setPriceUI();
//            if (updatedPromoCode != null) {
//                getViewDataBinding().promoCodeText.setText(updatedPromoCode.getCode());
//                getViewDataBinding().discountTotal.setText("-" + updatedPromoCode.processDiscount(getPriceTotal()) + "$");
//                getViewDataBinding().afterDiscountTotal.setText(updatedPromoCode.processPrice(getPriceTotal()) + "$");
//            }
//            else {
//                getViewDataBinding().promoCodeText.setText("");
//                getViewDataBinding().discountTotal.setText("-" + 0 + "$");
//                getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
//            }
        });
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

        AnimationDrawable animationDrawable4 = (AnimationDrawable) getViewDataBinding().loading4.getBackground();
        if (!animationDrawable4.isRunning()) {
            animationDrawable4.start();
        }

        AnimationDrawable animationDrawable5 = (AnimationDrawable) getViewDataBinding().loading5.getBackground();
        if (!animationDrawable5.isRunning()) {
            animationDrawable5.start();
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

        AnimationDrawable animationDrawable4 = (AnimationDrawable) getViewDataBinding().loading4.getBackground();
        if (animationDrawable4.isRunning()) {
            animationDrawable4.stop();
        }

        AnimationDrawable animationDrawable5 = (AnimationDrawable) getViewDataBinding().loading5.getBackground();
        if (animationDrawable5.isRunning()) {
            animationDrawable5.stop();
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
        displayOptionsForBag(View.GONE, View.GONE);
        dialogsManager.unregisterListener(this);
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof PromoCodeResponse) {
            promoCodeResponse.setValue((PromoCodeResponse) event);
        } else {
            promoCodeResponse.setValue(null);
        }
    }
    public void setPriceUI() {
        Log.d("setPriceUI", "price total before discount: ");
        getViewDataBinding().priceTotal.setText(getPriceTotal().toString() + "$");
        if (promoCodeResponse.getValue() == null) {
            getViewDataBinding().promoCodeText.setText("");
            getViewDataBinding().discountTotal.setText("-" + 0 + "$");
            getViewDataBinding().afterDiscountTotal.setText(getPriceTotal().toString() + "$");
        } else {
            getViewDataBinding().discountTotal.setText("-" + promoCodeResponse.getValue().processDiscount(getPriceTotal()).toString() + "$");
            getViewDataBinding().afterDiscountTotal.setText(promoCodeResponse.getValue().processPrice(getPriceTotal()).toString() + "$");
            getViewDataBinding().promoCodeText.setText(promoCodeResponse.getValue().getCode());
        }
        //            if (updatedPromoCode != null) {
//                getViewDataBinding().promoCodeText.setText(updatedPromoCode.getCode());
//                getViewDataBinding().discountTotal.setText("-" + updatedPromoCode.processDiscount(getPriceTotal()) + "$");
//                getViewDataBinding().afterDiscountTotal.setText(updatedPromoCode.processPrice(getPriceTotal()) + "$");
//            }
//            else {
//                getViewDataBinding().promoCodeText.setText("");
//                getViewDataBinding().discountTotal.setText("-" + 0 + "$");
//                getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
//            }
    }

    public Float getPriceTotal() {
        // get total price from String price (may contain $) of each product in list viewModel.getBagProducts().getValue()
        float totalPrice = 0f;
        for (int i = 0; i < viewModel.getBagProducts().getValue().size(); i++) {
            // convert each product's price into float
            String price = viewModel.getBagProducts().getValue().get(i).getPrice();
            price = price.replace("$", "");
            totalPrice += Float.parseFloat(price) * viewModel.getBagProducts().getValue().get(i).getQuantity();
        }
        return PromoCodeResponse.roundToTwoDecimalPlaces(totalPrice);
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
        long timestamp = System.currentTimeMillis(); // Current timestamp in milliseconds
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        // Generate 4 random alphanumeric characters
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }

        // Combine timestamp with random characters
        return "BEE-" + timestamp + builder.toString();
    }
    @Override
    public void onClick(View view) {
        int sum_quantity = 0;
        for (OrderDetailResponse orderDetailResponse : viewModel.getBagProducts().getValue()) {
            sum_quantity += orderDetailResponse.getQuantity();
        }
        OrderResponse orderResponse;
        if (promoCodeResponse.getValue() == null) {
            orderResponse = new OrderResponse(
                    "",
                    sum_quantity,
                    "processing",
                    generateUniqueOrderNumber(),
                    generateUniqueTrackingNumber(),
                    "",
                    "0$",
                    viewModel.getBagProducts().getValue());
        } else {
            orderResponse = new OrderResponse(
                    "",
                    sum_quantity,
                    "processing",
                    generateUniqueOrderNumber(),
                    generateUniqueTrackingNumber(),
                    "",
                    promoCodeResponse.getValue().processDiscount(getPriceTotal()) + "$",
                    viewModel.getBagProducts().getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("orderResponse", orderResponse);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.checkoutFragment, bundle);
    }

    @Override
    public void onChangeQuantity(String asin, List<Pair<String, String>> variations, boolean increase, int position) {
        viewModel.getCompositeDisposable().add(viewModel.getRepository().updateUserBagVariation(asin, variations, increase)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(success -> {
                                    if (success) {
//                                        Log.d("success", "success" + success);
//                                        viewModel.syncBagListsOnly();
//                        viewModel.getBagProducts().setValue(viewModel.getBagProducts().getValue());
                                        // Handle successful deletion
                                    } else {
                                        // Handle failure
                                    }
                                },
                                error -> {

                                })
        );
        List<OrderDetailResponse> orderDetailResponseList = viewModel.getBagProducts().getValue();
        int quantity = updateQuantity(orderDetailResponseList.get(position).getQuantity(), increase);
        orderDetailResponseList.get(position).setQuantity(quantity);
        viewModel.getBagProducts().setValue(orderDetailResponseList);
    }

    public static int updateQuantity(int quantity, boolean increase) {
        if (increase) return quantity + 1;
        return Math.max(quantity - 1, 0);
    }

    @Override
    public void onSaveToFavorites(int position, ImageView imageView) {
        viewModel.getRepository().saveUserVariation(Repository.UserVariation.FAVORITE, viewModel.getBagProducts().getValue().get(position));
        bottomBar.animateAddToFavorite(imageView, requireActivity().findViewById(R.id.main), Repository.UserVariation.FAVORITE);
    }

    @Override
    public void onDeleteFromList(String asin, List<Pair<String, String>> variations, int position) {
        List<OrderDetailResponse> orderDetailResponseList = viewModel.getBagProducts().getValue();
        orderDetailResponseList.remove(position);
        viewModel.getBagProducts().setValue(orderDetailResponseList);

        viewModel.getCompositeDisposable().add(viewModel.getRepository().deleteUserVariation(Repository.UserVariation.BAG, asin, variations)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                            if (success) {
                                // Handle successful update
//                                viewModel.getBagProducts().getValue().remove(position);
//                                viewModel.syncBagListsOnly();
                            } else {
                                // Handle failure
                            }
                        },
                        error -> {

                        })
        );
    }

    @Override
    public void onMoreVariationOption(int position) {
        OrderDetailResponse orderDetailResponse = viewModel.getBagProducts().getValue().get(position);
        moreVariationDialog dialog = moreVariationDialog.newInstance(dialogsManager, orderDetailResponse);
        dialog.setHasQuantity(false);
        dialog.show(requireActivity().getSupportFragmentManager(), "more_variation_dialog");
    }

    public void displayOptionsForBag(int visibility, int emptyBagVisibility) {
        Log.d("setPriceUI", "displayOptionsForBag: " + visibility + " " + emptyBagVisibility);
        getViewDataBinding().recyclerView.setVisibility(visibility);
        getViewDataBinding().emptyBag.setVisibility(emptyBagVisibility);
        getViewDataBinding().linearLayout.setVisibility(visibility);
        getViewDataBinding().textView2.setVisibility(visibility);
        getViewDataBinding().priceTotal.setVisibility(visibility);
        getViewDataBinding().promoCodeText.setVisibility(visibility);
        getViewDataBinding().discountTotal.setVisibility(visibility);
        getViewDataBinding().afterDiscountTotal.setVisibility(visibility);
        getViewDataBinding().promoCode.setVisibility(visibility);
        getViewDataBinding().discount.setVisibility(visibility);
        getViewDataBinding().checkOut.setVisibility(visibility);
    }
}
