package com.example.shopbee.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ProfileBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.base.BaseFragment;
import com.example.shopbee.ui.profile.adapter.ProfileAdapter;

public class ProfileFragment extends BaseFragment<ProfileBinding, ProfileViewModel> implements ProfileNavigator {
    ProfileBinding binding;
    ProfileAdapter adapter;
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
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        RecyclerView recyclerView = binding.recyclerViewProfile;
        adapter = new ProfileAdapter();
        adapter.setUpListProfileItem();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void myOrder() {

    }

    @Override
    public void shippingAddresses() {

    }

    @Override
    public void paymentMethods() {

    }

    @Override
    public void promocodes() {

    }

    @Override
    public void myReviews() {

    }

    @Override
    public void setting() {

    }

    @Override
    public void openMainActivity() {

    }
}
