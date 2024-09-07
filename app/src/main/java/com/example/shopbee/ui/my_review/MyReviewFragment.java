package com.example.shopbee.ui.my_review;

import androidx.databinding.library.baseAdapters.BR;

import com.example.shopbee.R;
import com.example.shopbee.databinding.MyReviewsBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;

public class MyReviewFragment extends BaseFragment<MyReviewsBinding, MyReviewViewModel> implements MyReviewNavigator {
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
}
