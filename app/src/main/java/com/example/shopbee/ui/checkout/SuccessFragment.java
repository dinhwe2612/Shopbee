package com.example.shopbee.ui.checkout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.SuccessBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.toolbar.ToolbarView;
import com.example.shopbee.ui.checkout.shipping.SuccessNavigator;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.main.MainActivity;

public class SuccessFragment extends BaseFragment<SuccessBinding, SuccessViewModel> implements SuccessNavigator {
    SuccessBinding binding;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.success;
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
        binding = getViewDataBinding();
        animateViews();
        binding.countinueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomeFragment();
            }
        });
        return binding.getRoot();
    }
    private void animateViews() {
        ObjectAnimator bag1Animator = ObjectAnimator.ofFloat(binding.bag1, "translationY", 300f, 0f);
        bag1Animator.setDuration(1000);
        bag1Animator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator bag2Animator = ObjectAnimator.ofFloat(binding.bag2, "translationY", 300f, 0f);
        bag2Animator.setDuration(1000);
        bag2Animator.setStartDelay(200);
        bag2Animator.setInterpolator(new AccelerateInterpolator());

        Animation fadeInAnimation = new AlphaAnimation(0f, 1f);
        fadeInAnimation.setDuration(1500);

        bag1Animator.start();
        bag2Animator.start();
        binding.background.startAnimation(fadeInAnimation);
        bag1Animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startOscillationAnimation(binding.bag1);
            }
        });

        bag2Animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startOscillationAnimation(binding.bag2);
            }
        });
    }

    private void startOscillationAnimation(View target) {
        ObjectAnimator oscillateAnimator = ObjectAnimator.ofFloat(target, "translationY", 0f, -30f, 30f, 0f);
        oscillateAnimator.setDuration(1000);
        oscillateAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        oscillateAnimator.setRepeatMode(ObjectAnimator.RESTART);
        oscillateAnimator.start();
    }

    @Override
    public void backToHomeFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.homeFragment);
    }
}
