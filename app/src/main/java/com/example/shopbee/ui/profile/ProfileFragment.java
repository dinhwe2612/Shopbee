package com.example.shopbee.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.ProfileBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.profile.adapter.ProfileAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends BaseFragment<ProfileBinding, ProfileViewModel> implements ProfileNavigator, ProfileAdapter.Listener {
    private ProfileBinding binding;
    private ProfileAdapter adapter;
    private UserResponse userResponse;
    private ListOrderResponse listOrderResponse;

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
        loadRealtimeData();

        binding.fullName.setText(userResponse.getFull_name());
        binding.email.setText(userResponse.getEmail());

        RecyclerView recyclerView = binding.recyclerViewProfile;
        adapter = new ProfileAdapter(this, getContentEachOption());
        adapter.setUpListProfileItem();
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void handleError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void myOrder() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.myOrderFragment);
    }

    @Override
    public void shippingAddresses() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.shippingFragment);
    }

    @Override
    public void paymentMethods() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.paymentFragment);
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
    public void loadRealtimeData(){

        viewModel.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
                userResponse = response;
            }
        });
        viewModel.getOrderResponse().observeForever(new Observer<ListOrderResponse>() {
            @Override
            public void onChanged(ListOrderResponse responses) {
                listOrderResponse = responses;
            }
        });
    }
    public List<String> getContentEachOption(){
        List<String> listContent = new ArrayList<>();
        if (listOrderResponse.getList_order().isEmpty()) listContent.add("You have no order yet.");
        else listContent.add("Already have " + String.valueOf(listOrderResponse.getList_order().size()) + " order(s)");
        if (userResponse.getAddress().isEmpty()){
            listContent.add("You have no address yet.");
        } else listContent.add(String.valueOf(userResponse.getAddress().size()) + " address(es)");
        listContent.add("Visa **3");
        listContent.add("You have special promocodes");
        listContent.add("Review for 3 item(s)");
        listContent.add("Notification, password");
        return listContent;
    }
}