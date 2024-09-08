package com.example.shopbee.ui.leave_feedback;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.databinding.LeaveFeedbackBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.writereivewdialog.WriteReviewEvent;
import com.example.shopbee.ui.leave_feedback.adapter.FeedbackAdapter;

import java.util.List;

import javax.inject.Inject;

public class LeaveFeedbackFragment extends BaseFragment<LeaveFeedbackBinding, LeaveFeedbackViewModel> implements LeaveFeedbackNavigator, FeedbackAdapter.OnReviewClickListener, DialogsManager.Listener {
    @Inject
    DialogsManager dialogsManager;
    private OrderResponse orderResponse;
    private LeaveFeedbackBinding binding;
    private FeedbackAdapter feedbackAdapter = new FeedbackAdapter();
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.leave_feedback;
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
        if (getArguments() == null) throw new IllegalArgumentException("Arguments LeaveFeedbackFragment cannot be null");
        orderResponse = getArguments().getParcelable("orderResponse");
        if (orderResponse == null) throw new IllegalArgumentException("OrderResponse LeaveFeedbackFragment cannot be null");
        binding = getViewDataBinding();
        observeReviewList();
        viewModel.syncIsReviewedList(orderResponse);
        viewModel.getInProgress().observe(getViewLifecycleOwner(), inProgress -> {
            if (inProgress) {
                binding.loading.setVisibility(View.VISIBLE);
                animateLoading();
            } else {
                binding.loading.setVisibility(View.GONE);
                stopLoadingAnimations();
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

    public void observeReviewList() {
        viewModel.getIsReviewedList().observe(getViewLifecycleOwner(), isReviewedList -> {
            feedbackAdapter.setIsReviewedList(isReviewedList);
            feedbackAdapter.notifyDataSetChanged();
        });
    }

    public void setUpRecyclerView() {
        feedbackAdapter.setOrderDetailResponseList(orderResponse.getOrder_detail());
        feedbackAdapter.setOnReviewClickListener(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(feedbackAdapter);
    }

    @Override
    public void onReviewClick(int position, boolean reviewed) {
        if (!reviewed) {
            dialogsManager.showWriteReviewDialog(orderResponse.getOrder_number(), orderResponse.getOrder_detail().get(position));
        }
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof WriteReviewEvent) {
            viewModel.saveReview((WriteReviewEvent) event);
            viewModel.syncIsReviewedList(orderResponse);
        }
    }
}
