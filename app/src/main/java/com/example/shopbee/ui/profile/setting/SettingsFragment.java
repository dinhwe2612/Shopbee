package com.example.shopbee.ui.profile.setting;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.SettingsBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.base.BaseFragment;
import com.example.shopbee.ui.profile.adapter.ProfileAdapter;

public class SettingsFragment extends BaseFragment<SettingsBinding, SettingsViewModel> implements SettingsNavigator {
    SettingsBinding binding;
    @Override
    public int getBindingVariable() {return 0;}
    @Override
    public int getLayoutId() {return R.layout.settings;}
    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
    }
}

