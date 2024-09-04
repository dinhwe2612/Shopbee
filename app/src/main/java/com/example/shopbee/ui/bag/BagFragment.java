package com.example.shopbee.ui.bag;

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
import com.example.shopbee.ui.common.dialogs.promoCode.PromoCodeDialog;
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
    boolean doneLoadingProducts = false;
    // handle blank bag
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
        if (viewModel.getRepository().getUserResponse() != null) {
            viewModel.syncBagLists();
            getViewDataBinding().recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            getViewDataBinding().recyclerView.setAdapter(bagAdapter);
            bagAdapter.setOnChangeQuantityListener(this);
            viewModel.getBagProducts().observe(getViewLifecycleOwner(), products -> {
                doneLoadingProducts = true;
                bagAdapter.setQuantities(viewModel.getBagQuantities().getValue());
                bagAdapter.setVariations(viewModel.getBagVariations().getValue());
                bagAdapter.setProducts(products);
                if (products.size() != 0) {
                    displayOptionsForBag(View.VISIBLE, View.GONE);
                    bagAdapter.notifyDataSetChanged();
                    getViewDataBinding().priceTotal.setText(getPriceTotal() + "$");
                    if (promoCodeResponse.getValue() != null) {
                        getViewDataBinding().promoCodeText.setText("");
                        getViewDataBinding().discountTotal.setText("-" + 0 + "$");
                        getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
                    } else {
                        getViewDataBinding().discountTotal.setText("-" + 0 + "$");
                        getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
                        getViewDataBinding().promoCodeText.setText("");
                    }
                }
                else {
                    displayOptionsForBag(View.GONE, View.VISIBLE);
                }
            });
            viewModel.getBagQuantities().observe(getViewLifecycleOwner(), lists -> {
                if (lists.isEmpty()) {
                    displayOptionsForBag(View.GONE, View.VISIBLE);
                }
                else {
                    displayOptionsForBag(View.VISIBLE, View.GONE);
                }
//                bagAdapter.setQuantities(viewModel.getBagQuantities().getValue());
//                bagAdapter.setVariations(viewModel.getBagVariations().getValue());
//                bagAdapter.setProducts(viewModel.getBagProducts().getValue());
//                bagAdapter.notifyDataSetChanged();
                if (doneLoadingProducts) {
                    if (!lists.isEmpty()) {
                        displayOptionsForBag(View.VISIBLE, View.GONE);
                        Log.d("update price", "update price");
                        getViewDataBinding().priceTotal.setText(getPriceTotal() + "$");
                        if (promoCodeResponse.getValue() != null) {
                            getViewDataBinding().promoCodeText.setText("");
                            getViewDataBinding().discountTotal.setText("-" + 0 + "$");
                            getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
                        } else {
                            getViewDataBinding().discountTotal.setText("-" + 0 + "$");
                            getViewDataBinding().afterDiscountTotal.setText(getPriceTotal() + "$");
                            getViewDataBinding().promoCodeText.setText("");
                        }
                    }
                    else {
                        displayOptionsForBag(View.GONE, View.VISIBLE);
                    }
                }
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
        else {
            getViewDataBinding().signIn.setVisibility(View.VISIBLE);
        }
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
        } else {
            promoCodeResponse.setValue(null);
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
    private String DateTimeToFormat(){
        Date now = new Date();
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm yyyy-MM-dd");
        String formattedDateTime = dateTimeFormat.format(now);
        return formattedDateTime;
    }
    @Override
    public void onClick(View view) {
        int sum_quantity = 0;
        for (int quantity : viewModel.getBagQuantities().getValue()) {
            sum_quantity += quantity;
        }
        List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
        for (int i = 0; i < viewModel.getBagProducts().getValue().size(); i++) {
            AmazonProductDetailsResponse product = viewModel.getBagProducts().getValue().get(i);
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse(product.getData().getAsin()
                    , product.getData().getProduct_title()
                    , viewModel.getBagQuantities().getValue().get(i)
                    , product.getData().getProduct_price()
                    , product.getData().getProduct_photo()
                    , viewModel.getBagVariations().getValue().get(i));
            orderDetailResponseList.add(orderDetailResponse);
        }
        OrderResponse orderResponse;
        if (promoCodeResponse.getValue() == null) {
            orderResponse = new OrderResponse(
                    DateTimeToFormat(),
                    sum_quantity,
                    "processing",
                    generateUniqueOrderNumber(),
                    generateUniqueTrackingNumber(),
                    "",
                    "0$",
                    orderDetailResponseList);
        } else {
            orderResponse = new OrderResponse(
                    DateTimeToFormat(),
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
    }

    @Override
    public void onChangeQuantity(String asin, List<Pair<String, String>> variations, boolean increase) {
        viewModel.getCompositeDisposable().add(viewModel.getRepository().updateUserBagVariation(asin, variations, increase)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(success -> {
                                    if (success) {
                                        Log.d("success", "success" + success);
                                        viewModel.syncBagListsOnly();
//                        viewModel.getBagProducts().setValue(viewModel.getBagProducts().getValue());
                                        // Handle successful deletion
                                    } else {
                                        // Handle failure
                                    }
                                },
                                error -> {

                                })
        );
    }

    @Override
    public void onSaveToFavorites(String asin, List<Pair<String, String>> variations, ImageView imageView) {
        viewModel.getRepository().saveUserVariation(Repository.UserVariation.FAVORITE, asin, variations, null);
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.getBottomBar().animateAddToFavorite(imageView, mainActivity.findViewById(R.id.main), Repository.UserVariation.FAVORITE);
    }

    @Override
    public void onDeleteFromList(String asin, List<Pair<String, String>> variations, int position) {
        viewModel.getCompositeDisposable().add(viewModel.getRepository().deleteUserVariation(Repository.UserVariation.BAG, asin, variations)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                            if (success) {
                                // Handle successful update
                                viewModel.syncBagListsOnly();
                                viewModel.getBagProducts().getValue().remove(position);
                            } else {
                                // Handle failure
                            }
                        },
                        error -> {

                        })
        );
    }
    public void displayOptionsForBag(int visibility, int emptyBagVisibility) {
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
