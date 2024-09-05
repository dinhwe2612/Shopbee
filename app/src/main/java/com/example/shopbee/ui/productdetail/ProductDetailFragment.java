package com.example.shopbee.ui.productdetail;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.databinding.ProductDetailBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.imagepickerdialog.ImagePickerEvent;
import com.example.shopbee.ui.common.dialogs.optiondialog.OptionEvent;
import com.example.shopbee.ui.main.MainActivity;
import com.example.shopbee.ui.productdetail.adapter.AboutProductAdapter;
import com.example.shopbee.ui.productdetail.adapter.ProductDetailAdapter;
import com.example.shopbee.ui.productdetail.adapter.ProductPhotosAdapter;
import com.example.shopbee.ui.productdetail.adapter.RecommendedAdapter;
import com.example.shopbee.utils.NetworkUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ProductDetailFragment extends BaseFragment<ProductDetailBinding, ProductDetailViewModel> implements ProductDetailNavigator, DialogsManager.Listener{
    ProductDetailBinding binding;
    String asin;
    ProductPhotosAdapter productPhotosAdapter = new ProductPhotosAdapter();
    ProductDetailAdapter productDetailAdapter = new ProductDetailAdapter();
    AboutProductAdapter aboutProductAdapter = new AboutProductAdapter();
    RecommendedAdapter recommendedAdapter;
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
        binding.seeAll.setOnClickListener(v -> viewModel.getNavigator().goToReviews());
        binding.productName.setOnClickListener(v->{
            if (binding.productName.getMaxLines() == 2) {
                binding.productName.setMaxLines(Integer.MAX_VALUE);
            } else {
                binding.productName.setMaxLines(2);
            }
        });
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
            binding.totalPhotos.setText("/" + imageUrls.size());
            // bind product details
            List<Pair<String, String>> productDetailsList = new ArrayList<>();
            HashMap<String, String> productDetailsMap = productDetails.getData().getProduct_details();
            for (String key : productDetailsMap.keySet()) {
                productDetailsList.add(new Pair<>(key, productDetailsMap.get(key)));
                Log.d("productDetails", key + " " + productDetailsMap.get(key));
            }
            productDetailAdapter.setProductDetail(productDetailsList);
            // bind about product
            aboutProductAdapter.setAbouts(productDetails.getData().getAbout_product());
            // bind product name
            binding.productName.setText(productDetails.getData().getProduct_title());
            binding.searchBar.setText(productDetails.getData().getProduct_title());
            // bind description
            binding.descriptionContent.setText(productDetails.getData().getProduct_description());
            binding.descriptionContent.addShowMoreText("");
            binding.descriptionContent.addShowLessText("");
            binding.descriptionContent.setShowingLine(3);
            // bind number of reviews
            Integer numReviews = productDetails.getData().getProduct_num_ratings();
            if (numReviews == null) numReviews = 0;
            binding.numRatings.setText("(" + numReviews + ")");
            binding.numGlobalRatings.setText(numReviews + " global ratings");
            // bind customers say
            String customersSay = productDetails.getData().getCustomers_say();
            if (customersSay == null) binding.aigenerated.setVisibility(View.GONE);
            else binding.customersayContent.setText(customersSay);
            // bind rating stars
            String ratingStars = productDetails.getData().getProduct_star_rating();
            if (ratingStars == null) ratingStars = "0";
            binding.ratingOverall.setText(ratingStars);
            binding.ratingBar.setRating(Float.parseFloat(ratingStars));
            // bind price
            String currentPrice = productDetails.getData().getProduct_price();
            String originalPrice = productDetails.getData().getProduct_original_price();
            if (originalPrice == null) {
                binding.currentPrice.setText(currentPrice);
            } else {
                binding.currentPrice.setText(currentPrice);
                binding.originalPrice.setText(originalPrice);
            }
            // sync recommended products
            HashMap<String, String> categoryMap = new HashMap<>();
            String categories = productDetails.getData().getCategory_path().stream()
                    .map(AmazonProductDetailsResponse.Data.Category::getId)
                    .collect(Collectors.joining(","));
            categoryMap.put("category_id", categories);
            viewModel.syncProductByCategory(categoryMap);
        });
        viewModel.getProductByCategory().observe(getViewLifecycleOwner(), productByCategory -> {
            recommendedAdapter.setProducts(productByCategory.getData().getProducts());
        });
    }

    void setUpRecyclerView() {
        // product photos
        binding.prodPhotosRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.prodPhotosRCV.setAdapter(productPhotosAdapter);
        new PagerSnapHelper().attachToRecyclerView(binding.prodPhotosRCV);
        binding.prodPhotosRCV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = layoutManager.findFirstVisibleItemPosition();
                binding.curPhoto.setText((position + 1)+"");
            }
        });
        // product details
        binding.pddetailsRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.pddetailsRCV.setAdapter(productDetailAdapter);
        // about product
        binding.aboutProductRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.aboutProductRCV.setAdapter(aboutProductAdapter);
        // recommended
        recommendedAdapter = new RecommendedAdapter(getContext());
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
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, amazonProductDetailsResponse.getData().getProduct_url());
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(sendIntent);
    }

    @Override
    public void option() {

    }

    @Override
    public void goToReviews() {
        Bundle bundle = new Bundle();
        bundle.putString("asin", asin);
        NavHostFragment.findNavController(this).navigate(R.id.reviewFragment, bundle);
    }

    @Override
    public void tryItOn() {
        dialogsManager.showImagePickerDialog();
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
                MainActivity mainActivity = (MainActivity) getActivity();
                LinearLayoutManager layoutManager = (LinearLayoutManager) binding.prodPhotosRCV.getLayoutManager();
                mainActivity.getBottomBar().animateAddToFavorite((ImageView) layoutManager.getChildAt(layoutManager.findFirstVisibleItemPosition()), mainActivity.findViewById(R.id.main), Repository.UserVariation.FAVORITE);
            }
            else if (optionEvent.getName() == "ADD TO BAG") {
                // print out optionEvent
                viewModel.getRepository().saveUserVariation(Repository.UserVariation.BAG, asin, optionEvent.getOptions(), optionEvent.getQuantity());
                MainActivity mainActivity = (MainActivity) getActivity();
                LinearLayoutManager layoutManager = (LinearLayoutManager) binding.prodPhotosRCV.getLayoutManager();
                mainActivity.getBottomBar().animateAddToFavorite((ImageView) layoutManager.getChildAt(layoutManager.findFirstVisibleItemPosition()), mainActivity.findViewById(R.id.main), Repository.UserVariation.BAG);
            }
        } else if (event instanceof ImagePickerEvent) {
            ImagePickerEvent imagePickerEvent = (ImagePickerEvent) event;
            // call api to try it on
            viewModel.getTryOnImage().observe(getViewLifecycleOwner(), bitmap -> {
                dialogsManager.showImagePreviewDialog(bitmap);
            });
            viewModel.syncTryOnImage(imagePickerEvent.getBitmap(), amazonProductDetailsResponse.getData().getProduct_photo());
            Log.d("tryItOn", amazonProductDetailsResponse.getData().getProduct_photo());
        }
    }
}
