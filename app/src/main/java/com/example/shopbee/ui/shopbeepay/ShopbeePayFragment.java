package com.example.shopbee.ui.shopbeepay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ShopbeepayBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;

public class ShopbeePayFragment extends BaseFragment<ShopbeepayBinding, ShopbeePayViewModel> implements ShopbeePayNavigator {
    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shopbeepay;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    ShopbeepayBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        binding.eye.setOnClickListener(v->{
            if (binding.unseen.getVisibility() == View.GONE) {
                binding.unseen.setVisibility(View.VISIBLE);
                binding.money.setText("***$");
            } else {
                binding.unseen.setVisibility(View.GONE);
                binding.money.setText("0$");
            }
        });
        binding.backButton.setOnClickListener(v->{
            navigateUp();
        });
        return view;
    }

    @Override
    public void navigateUp() {
        NavHostFragment.findNavController(this).navigateUp();
    }
}
