package com.example.shopbee.ui.my_review;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.databinding.MyReviewsBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.my_review.adapter.MyReviewAdapter;

import javax.inject.Inject;

public class MyReviewFragment extends BaseFragment<MyReviewsBinding, MyReviewsViewModel> implements MyReviewNavigator, DialogsManager.Listener, MyReviewAdapter.MoreVariationClickListener {
    @Inject
    DialogsManager dialogsManager;
    private MyReviewsBinding binding;
    private MyReviewAdapter myReviewAdapter = new MyReviewAdapter();
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_reviews;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        observeReviewList();
        viewModel.syncReviewList();
        viewModel.getInProgress().observe(getViewLifecycleOwner(), inProgress -> {
            if (inProgress) {
                binding.loading.setVisibility(View.VISIBLE);
                animateLoading();
            } else {
                stopLoadingAnimations();
                binding.loading.setVisibility(View.GONE);
                setUpRecyclerView();
            }
        });
        return binding.getRoot();
    }
    public void animateLoading() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) binding.loading1.getBackground();
        if (!animationDrawable1.isRunning()) {
            animationDrawable1.start();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) binding.loading2.getBackground();
        if (!animationDrawable2.isRunning()) {
            animationDrawable2.start();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) binding.loading3.getBackground();
        if (!animationDrawable3.isRunning()) {
            animationDrawable3.start();
        }

        AnimationDrawable animationDrawable4 = (AnimationDrawable) binding.loading4.getBackground();
        if (!animationDrawable4.isRunning()) {
            animationDrawable4.start();
        }
    }

    public void stopLoadingAnimations() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) binding.loading1.getBackground();
        if (animationDrawable1.isRunning()) {
            animationDrawable1.stop();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) binding.loading2.getBackground();
        if (animationDrawable2.isRunning()) {
            animationDrawable2.stop();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) binding.loading3.getBackground();
        if (animationDrawable3.isRunning()) {
            animationDrawable3.stop();
        }

        AnimationDrawable animationDrawable4 = (AnimationDrawable) binding.loading4.getBackground();
        if (animationDrawable4.isRunning()) {
            animationDrawable4.stop();
        }
    }
    public void setUpRecyclerView() {
        myReviewAdapter.setMoreVariationClickListener(this);
        binding.reviewRCV.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.reviewRCV.setAdapter(myReviewAdapter);
    }
    public void observeReviewList() {
        viewModel.getReviewList().observe(getViewLifecycleOwner(), reviewList -> {
            myReviewAdapter.setReviewList(reviewList);
            myReviewAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDialogEvent(Object event) {

    }

    @Override
    public void onMoreVariationClick(int position) {

    }
}
