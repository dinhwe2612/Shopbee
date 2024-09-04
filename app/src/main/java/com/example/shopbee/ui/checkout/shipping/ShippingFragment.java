package com.example.shopbee.ui.checkout.shipping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AddressResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.ShippingBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.checkout.adapter.PaymentAdapter;
import com.example.shopbee.ui.checkout.adapter.ShippingAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;

public class ShippingFragment extends BaseFragment<ShippingBinding, ShippingViewModel> implements ShippingNavigator, ShippingAdapter.Listener {
    private ShippingBinding binding;
    private UserResponse userResponse;
    private ShippingAdapter shippingAdapter;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.shipping;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        loadRealtimeData();
        RecyclerView recyclerView = binding.recyclerViewShipping;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int positionDef = -1;
        for (int i = 0; i < userResponse.getAddress().size(); i++) {
            if (userResponse.getAddress().get(i).getDef()){
                positionDef = i;
            }
        }
        shippingAdapter = new ShippingAdapter(this, userResponse.getAddress(), positionDef, userResponse.getFull_name());
        recyclerView.setAdapter(shippingAdapter);

        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCheckout();
            }
        });
        binding.addNewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAddress();
            }
        });
        return binding.getRoot();
    }

    private void loadRealtimeData() {
        viewModel.getUserResponse().observeForever(response -> userResponse = response);
    }

    @Override
    public void onClickItems(int position) {
        for (int i = 0; i < userResponse.getAddress().size(); i++) {
            userResponse.getAddress().get(i).setDef(false);
        }
        userResponse.getAddress().get(position).setDef(true);
    }

    @Override
    public void onEditItems(int position) {
        AddressResponse addressResponse = userResponse.getAddress().get(position);
        editAddress(addressResponse, position);
    }

    @Override
    public void addNewAddress() {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putString("modify", "add");
        bundle.putInt("position", -1);
        navController.navigate(R.id.modifyAddressFragment, bundle);
    }

    @Override
    public void editAddress(AddressResponse addressResponse, int position) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("modify", "edit");
        bundle.putString("name", userResponse.getFull_name());
        bundle.putString("address", addressResponse.getAddress());
        bundle.putString("city", addressResponse.getCity());
        bundle.putString("state", addressResponse.getState());
        bundle.putString("zip", addressResponse.getZip_code());
        bundle.putString("country", addressResponse.getCountry());
        navController.navigate(R.id.modifyAddressFragment, bundle);
    }

    @Override
    public void backToCheckout() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }
}
