package com.example.shopbee.ui.review;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.RatingAndReviewsBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.review.adapter.ReviewAdapter;

import java.util.HashMap;

import javax.inject.Inject;

public class ReviewFragment extends BaseFragment<RatingAndReviewsBinding, ReviewViewModel> implements DialogsManager.Listener, ReviewNavigator, ReviewAdapter.Listener {
    String asin;
    int page = 1;
    ReviewAdapter reviewAdapter;
    RatingAndReviewsBinding binding;
    @Inject
    DialogsManager dialogsManager;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.rating_and_reviews;
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
        if (getArguments() == null) {
            throw new IllegalArgumentException("Arguments of ReviewFragment cannot be null");
        }
        asin = getArguments().getString("asin");
        String ratingNumber = getArguments().getString("ratingNumber");
        if (ratingNumber == null) {
            ratingNumber = "0";
        }
        binding.ratingNumber.setText(ratingNumber);
        Log.d("ReviewFragment", "onCreateView: " + Float.parseFloat(ratingNumber));
        binding.ratingBar.setRating(Float.parseFloat(ratingNumber));
        binding.seeMore.setOnClickListener(v -> loadReview());
        viewModel.getInProgress().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                dialogsManager.showLoadingDialog();
            } else {
                dialogsManager.dismiss(dialogsManager.LOADING_DIALOG);
            }
        });
        setUpRecyclerView();
        observeData();
        syncData();

        return getViewDataBinding().getRoot();
    }
    void syncData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("asin", asin);
        params.put("page", String.valueOf(page));
        ++page;
        viewModel.syncReview(params);
    }
    void observeData() {
        viewModel.getProductReviews().observe(getViewLifecycleOwner(), reviews -> {
            Log.d("ReviewFragment", "observeData: " + reviews.getData().getReviews().size() + " " + page);
            if (reviews.getData().getReviews().isEmpty()) {
                Log.d("ReviewFragment", "observeData: " + reviews.getData().getReviews().size());
                binding.seeMore.setText("No more reviews");
                binding.seeMore.setEnabled(false);
            } else {
                reviewAdapter.addReviews(reviews);
                if (page == 2) {
                    binding.currentReviews.setText(reviews.getData().getTotal_reviews() + " reviews");
                    binding.totalRating.setText(reviews.getData().getTotal_ratings() + " ratings");
                }
            }
        });
    }
    void setUpRecyclerView() {
        reviewAdapter = new ReviewAdapter(this);
        binding.reviewRCV.setAdapter(reviewAdapter);
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

    @Override
    public void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    @Override
    public void loadReview() {
        syncData();
    }

    @Override
    public void showImage(Bitmap bitmap) {
        dialogsManager.showImagePreviewDialog(bitmap);
    }
}
