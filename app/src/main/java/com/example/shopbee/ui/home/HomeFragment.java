package com.example.shopbee.ui.home;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.shopbee.R;
import com.example.shopbee.databinding.HomeBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.base.BaseFragment;

public class HomeFragment extends BaseFragment<HomeBinding, HomeViewModel> implements HomeNavigator {
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.home;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HomeFragment", "onCreate: ");
    }
}
