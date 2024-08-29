package com.example.shopbee.ui.productdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ProductDetailBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;

public class ProductDetailFragment extends BaseFragment<ProductDetailBinding, ProductDetailViewModel> implements ProductDetailNavigator{
    ProductDetailBinding binding;
    @Override
    public int getBindingVariable() {
        return 0;
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
        binding = getViewDataBinding();

        return binding.getRoot();
    }

    @Override
    public void addToBag() {

    }

    @Override
    public void buyNow() {

    }

    @Override
    public void goToBag() {

    }

    @Override
    public void addFavorite() {

    }

    @Override
    public void shareProduct() {

    }

    @Override
    public void option() {

    }
}
