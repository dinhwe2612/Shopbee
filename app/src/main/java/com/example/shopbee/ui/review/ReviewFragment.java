package com.example.shopbee.ui.review;

import android.os.Bundle;
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

public class ReviewFragment extends BaseFragment<RatingAndReviewsBinding, ReviewViewModel> implements DialogsManager.Listener, ReviewNavigator {
    String asin;
    ReviewAdapter reviewAdapter = new ReviewAdapter();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        if (getArguments() == null) {
            throw new IllegalArgumentException("Arguments of ReviewFragment cannot be null");
        }
        asin = getArguments().getString("asin");
        observeData();
        syncData();
        setUpRecyclerView();
        return getViewDataBinding().getRoot();
    }
    void syncData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("asin", asin);
        viewModel.syncReview(params);
    }
    void observeData() {
        viewModel.getProductReviews().observe(getViewLifecycleOwner(), reviews -> {
            reviewAdapter.setReviews(reviews);
        });
    }
    void setUpRecyclerView() {
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
}
