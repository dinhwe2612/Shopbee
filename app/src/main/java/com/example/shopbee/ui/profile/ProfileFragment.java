package com.example.shopbee.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ProfileBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.base.BaseFragment;
import com.example.shopbee.ui.profile.adapter.ProfileAdapter;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends BaseFragment<ProfileBinding, ProfileViewModel> implements ProfileNavigator, ProfileAdapter.Listener {

    ProfileBinding binding;
    ProfileAdapter adapter;
    Map<Integer, Runnable> triggered = Map.of(
            0, this::myOrder,
            1, this::shippingAddresses,
            2, this::paymentMethods,
            3, this::promocodes,
            4, this::myReviews,
            5, this::setting
            );
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
        adapter = new ProfileAdapter(this);
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
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.settingsFragment);
    }

    @Override
    public void openMainActivity() {

    }

    @Override
    public void onItemClicked(int position) {
        triggered.get(position).run();
    }
}
