package com.example.shopbee.ui.profile;

import androidx.databinding.library.baseAdapters.BR;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ProfileBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.base.BaseFragment;

public class ProfileFragment extends BaseFragment<ProfileBinding, ProfileViewModel> implements ProfileNavigator {
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.profile;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
}
