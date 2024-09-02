package com.example.shopbee.ui.productdetail;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.example.shopbee.R;
import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.databinding.ProductDetailBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.optiondialog.OptionEvent;
import com.example.shopbee.ui.productdetail.adapter.AboutProductAdapter;
import com.example.shopbee.ui.productdetail.adapter.ProductDetailAdapter;
import com.example.shopbee.ui.productdetail.adapter.ProductPhotosAdapter;
import com.example.shopbee.ui.productdetail.adapter.RecommendedAdapter;
import com.example.shopbee.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class ProductDetailFragment extends BaseFragment<ProductDetailBinding, ProductDetailViewModel> implements ProductDetailNavigator, DialogsManager.Listener{
    ProductDetailBinding binding;
    String asin;
    ProductPhotosAdapter productPhotosAdapter = new ProductPhotosAdapter();
    ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter();
    AboutProductAdapter aboutProductAdapter = new AboutProductAdapter();
    RecommendedAdapter recommendedAdapter = new RecommendedAdapter();
    AmazonProductDetailsResponse amazonProductDetailsResponse = new AmazonProductDetailsResponse();
    @Inject
    DialogsManager dialogsManager;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.product_detail;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() == null) throw new IllegalArgumentException("Arguments ProductDetailFragment cannot be null");
        asin = getArguments().getString("asin");
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        setUpRecyclerView();
        syncData(asin);
        observeData();
        binding.sparkButton.setOnClickListener(v -> viewModel.getNavigator().addFavorite());
        return binding.getRoot();
    }

    void syncData(String asin) {
        viewModel.syncProductDetails(NetworkUtils.createProductDetailsQuery(asin));
    }

    void observeData() {
        viewModel.getProductDetails().observe(getViewLifecycleOwner(), productDetails -> {
            amazonProductDetailsResponse = productDetails;
            // bind photo url
            List<String> imageUrls = new ArrayList<>();
            imageUrls.add(productDetails.getData().getProduct_photo());
            imageUrls.addAll(productDetails.getData().getProduct_photos());
            imageUrls.remove(null);
            productPhotosAdapter.setUrlImages(imageUrls);
            // bind product details
            List<Pair<String, String>> productDetailsList = new ArrayList<>();
            HashMap<String, String> productDetailsMap = productDetails.getData().getProduct_details();
            for (String key : productDetailsMap.keySet()) {
                productDetailsList.add(new Pair<>(key, productDetailsMap.get(key)));
            }
            productDetailAdapter.setProductDetail(productDetailsList);
            // bind about product
            aboutProductAdapter.setAbouts(productDetails.getData().getAbout_product());
            // bind product name
            binding.productName.setText(productDetails.getData().getProduct_title());
            // bind description
            binding.descriptionContent.setText(productDetails.getData().getProduct_description());
            // bind number of reviews
            binding.numRatings.setText("(" + productDetails.getData().getProduct_num_ratings() + ")");
            binding.numGlobalRatings.setText(productDetails.getData().getProduct_num_ratings() + " global ratings");
            // bind customers say
            binding.customersayContent.setText(productDetails.getData().getCustomers_say());
            // bind rating stars
            binding.ratingOverall.setText(productDetails.getData().getProduct_star_rating());
            binding.ratingBar.setRating(Float.parseFloat(productDetails.getData().getProduct_star_rating()));
            // bind price
            if (productDetails.getData().getProduct_original_price() == null) {
                binding.currentPrice.setText(productDetails.getData().getProduct_price());
            } else {
                binding.currentPrice.setText(productDetails.getData().getProduct_price());
                binding.originalPrice.setText(productDetails.getData().getProduct_original_price());
            }
        });
    }

    void setUpRecyclerView() {
        // product photos
        binding.prodPhotosRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.prodPhotosRCV.setAdapter(productPhotosAdapter);
        new PagerSnapHelper().attachToRecyclerView(binding.prodPhotosRCV);
        // product details
        binding.pddetailsRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.pddetailsRCV.setAdapter(productDetailAdapter);
        // about product
        binding.aboutProductRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.aboutProductRCV.setAdapter(aboutProductAdapter);
        // recommended
        binding.recommendRCV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recommendRCV.setAdapter(recommendedAdapter);
    }

    @Override
    public void addToBag() {
        Toast.makeText(getContext(), "Add to bag", Toast.LENGTH_SHORT).show();
        dialogsManager.showOptionDialog("ADD TO BAG", amazonProductDetailsResponse.getData().getProduct_price(), amazonProductDetailsResponse.getData().getProduct_photo(), amazonProductDetailsResponse.getData().getProduct_variations());
    }

    @Override
    public void buyNow() {
        Toast.makeText(getContext(), "Buy now", Toast.LENGTH_SHORT).show();
        dialogsManager.showOptionDialog("BUY NOW", amazonProductDetailsResponse.getData().getProduct_price(), amazonProductDetailsResponse.getData().getProduct_photo(), amazonProductDetailsResponse.getData().getProduct_variations());
    }

    @Override
    public void goToBag() {
        Toast.makeText(getContext(), "Go to bag", Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).navigate(R.id.bagFragment);
    }

    @Override
    public void addFavorite() {
        dialogsManager.showOptionDialog("ADD FAVORITE", amazonProductDetailsResponse.getData().getProduct_price(), amazonProductDetailsResponse.getData().getProduct_photo(), amazonProductDetailsResponse.getData().getProduct_variations());
    }

    @Override
    public void shareProduct() {

    }

    @Override
    public void option() {

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
        if (event instanceof OptionEvent) {
            OptionEvent optionEvent = (OptionEvent) event;
            if (optionEvent.getName() == "ADD FAVORITE") {
                viewModel.getRepository().saveUserVariation(Repository.UserVariation.FAVORITE, asin, optionEvent.getOptions(), null);
            }
        }
    }
}
