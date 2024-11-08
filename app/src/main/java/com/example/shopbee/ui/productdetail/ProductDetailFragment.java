package com.example.shopbee.ui.productdetail;

import android.app.AlertDialog;
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
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.BuyNowResponse;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.databinding.ProductDetailBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.buy_now.BuyNowFragment;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.imagepickerdialog.ImagePickerEvent;
import com.example.shopbee.ui.common.dialogs.optiondialog.OptionEvent;
import com.example.shopbee.ui.main.MainActivity;
import com.example.shopbee.ui.productdetail.adapter.AboutProductAdapter;
import com.example.shopbee.ui.productdetail.adapter.ProductDetailAdapter;
import com.example.shopbee.ui.productdetail.adapter.ProductPhotosAdapter;
import com.example.shopbee.ui.productdetail.adapter.RecommendedAdapter;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
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
        viewModel.setNavigator(this);
    }

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (getArguments() == null) throw new IllegalArgumentException("Arguments ProductDetailFragment cannot be null");
        asin = getArguments().getString("asin");
        binding = getViewDataBinding();
        setUpRecyclerView();
        syncData(asin);
        observeData();
        binding.productName.setOnClickListener(v->{
            if (binding.productName.getMaxLines() == 2) {
                binding.productName.setMaxLines(Integer.MAX_VALUE);
            } else {
                binding.productName.setMaxLines(2);
            }
        });
        binding.sparkButton.setOnClickListener(v->{
            addFavorite();
        });
        viewModel.getInProgress().observe(getViewLifecycleOwner(), inProgress -> {
            if (inProgress) {
                dialogsManager.showLoadingDialog();
            } else {
                dialogsManager.dismiss(dialogsManager.LOADING_DIALOG);
            }
        });
        return binding.getRoot();
    }
    void showTryItOnButtonOrNot(List<AmazonProductDetailsResponse.Data.Category> categoryList) {
        binding.tryItOn.setVisibility(View.GONE);
        for (AmazonProductDetailsResponse.Data.Category category : categoryList) {
            if (CategoriesHashMap.getInstance().getClothingCategories().contains(category.getId())) {
                binding.tryItOn.setVisibility(View.VISIBLE);
            }
        }
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
            showTryItOnButtonOrNot(productDetails.getData().getCategory_path());
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
        dialogsManager.showOptionDialog("ADD TO BAG", amazonProductDetailsResponse.getData().getProduct_price(), amazonProductDetailsResponse.getData().getProduct_photo(), amazonProductDetailsResponse.getData().getProduct_variations());
    }

    @Override
    public void buyNow() {
        dialogsManager.showOptionDialog("BUY NOW", amazonProductDetailsResponse.getData().getProduct_price(), amazonProductDetailsResponse.getData().getProduct_photo(), amazonProductDetailsResponse.getData().getProduct_variations());
    }

    @Override
    public void goToBag() {
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
        PopupMenu popupMenu = new PopupMenu(getContext(), binding.iconMore);
        popupMenu.inflate(R.menu.product_detail_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_item_report) {
                Toast.makeText(getContext(), "Your report has been sent", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public void goToReviews() {
        Bundle bundle = new Bundle();
        bundle.putString("asin", asin);
        bundle.putString("ratingNumber", amazonProductDetailsResponse.getData().getProduct_star_rating());
        NavHostFragment.findNavController(this).navigate(R.id.reviewFragment, bundle);
    }

    @Override
    public void tryItOn() {
        dialogsManager.showImagePickerDialog();
    }

    @Override
    public void search() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.userSearchFragment);
    }

    @Override
    public void navigateUp() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    @Override
    public void tryItOnFailed() {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Try it on failed")
                .setMessage("Your avatar is not suitable for this product or the product is not available for try it on.")
                .setPositiveButton("OK", (dialog1, which) -> {
                    dialog1.dismiss();
                })
                .create();
        dialog.show();
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
            AmazonProductDetailsResponse.Data data = amazonProductDetailsResponse.getData();
            OrderDetailResponse orderDetailResponse = new OrderDetailResponse(asin,
                    data.getProduct_title(),
                    optionEvent.getQuantity(),
                    data.getProduct_price(),
                    data.getProduct_photo(),
                    optionEvent.getOptions(),
                    data.getProduct_star_rating(),
                    data.getProduct_num_ratings());
            if (optionEvent.getName() == "ADD FAVORITE") {
                viewModel.getRepository().saveUserVariation(Repository.UserVariation.FAVORITE, orderDetailResponse);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) binding.prodPhotosRCV.getLayoutManager();
//                mainActivity.getBottomBar().animateAddToFavorite((ImageView) layoutManager.getChildAt(layoutManager.findFirstVisibleItemPosition()), mainActivity.findViewById(R.id.main), Repository.UserVariation.FAVORITE);
                bottomBar.animateAddToFavorite(productPhotosAdapter.getCurrentView(), requireActivity().findViewById(R.id.main), Repository.UserVariation.FAVORITE);
            }
            else if (optionEvent.getName() == "ADD TO BAG") {
                // print out optionEvent
//                orderDetailResponse.setQuantity(optionEvent.getQuantity());
                viewModel.getRepository().saveUserVariation(Repository.UserVariation.BAG, orderDetailResponse);
//                LinearLayoutManager layoutManager = (LinearLayoutManager) binding.prodPhotosRCV.getLayoutManager();
                bottomBar.animateAddToFavorite(productPhotosAdapter.getCurrentView(), requireActivity().findViewById(R.id.main), Repository.UserVariation.BAG);
            }
            else if (optionEvent.getName() == "BUY NOW") {
//                viewModel.getRepository().saveUserVariation(Repository.UserVariation.CHECKOUT, orderDetailResponse);
                Bundle bundle = new Bundle();
                List<OrderDetailResponse> orderDetailResponseList = new ArrayList<>();
                orderDetailResponseList.add(orderDetailResponse);
                BuyNowResponse buyNowResponse = new BuyNowResponse(orderDetailResponseList);
                bundle.putParcelable("buyNowResponse", buyNowResponse);
                NavHostFragment.findNavController(this).navigate(R.id.buyNowFragment, bundle);
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
