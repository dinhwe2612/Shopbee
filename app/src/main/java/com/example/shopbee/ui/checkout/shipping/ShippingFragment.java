package com.example.shopbee.ui.checkout.shipping;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShippingFragment extends BaseFragment<ShippingBinding, ShippingViewModel> implements ShippingNavigator, ShippingAdapter.Listener {
    private ShippingBinding binding;
    private UserResponse userResponse;
    private ShippingAdapter shippingAdapter;
    private CardView fab, write_fab, map_fab;
    private Boolean isAllFabsVisible;
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
        ExtendFloatingActionButton();
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
    public void addNewAddressByHand() {
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
    public void ExtendFloatingActionButton(){
        fab = binding.addNewCard;
        write_fab = binding.addByHand;
        map_fab = binding.addByLocate;
        isAllFabsVisible = false;

        write_fab.setVisibility(View.GONE);
        map_fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAllFabsVisible){
                    showFabs();
                } else {
                    hideFabs();
                }
            }
        });
        write_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAddressByHand();
            }
        });
        map_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void showFabs() {
        write_fab.setVisibility(View.VISIBLE);
        map_fab.setVisibility(View.VISIBLE);

        ObjectAnimator animatorXWrite = ObjectAnimator.ofFloat(write_fab, "translationX", -30f); // Replace with actual position
        ObjectAnimator animatorYWrite = ObjectAnimator.ofFloat(write_fab, "translationY", -150f); // Replace with actual position

        ObjectAnimator animatorXMap = ObjectAnimator.ofFloat(map_fab, "translationX", -150f); // Replace with actual position
        ObjectAnimator animatorYMap = ObjectAnimator.ofFloat(map_fab, "translationY", 0f); // Replace with actual position

        animatorXWrite.setDuration(300);
        animatorYWrite.setDuration(300);
        animatorXMap.setDuration(300);
        animatorYMap.setDuration(300);

        animatorXWrite.start();
        animatorYWrite.start();
        animatorXMap.start();
        animatorYMap.start();

        isAllFabsVisible = true;
    }
    private void hideFabs() {
        ObjectAnimator animatorXWrite = ObjectAnimator.ofFloat(write_fab, "translationX", 0f);
        ObjectAnimator animatorYWrite = ObjectAnimator.ofFloat(write_fab, "translationY", 0f);

        ObjectAnimator animatorXMap = ObjectAnimator.ofFloat(map_fab, "translationX", 0f);
        ObjectAnimator animatorYMap = ObjectAnimator.ofFloat(map_fab, "translationY", 0f);

        animatorXWrite.setDuration(300);
        animatorYWrite.setDuration(300);
        animatorXMap.setDuration(300);
        animatorYMap.setDuration(300);

        animatorXWrite.start();
        animatorYWrite.start();
        animatorXMap.start();
        animatorYMap.start();

        animatorYMap.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                write_fab.setVisibility(View.GONE);
                map_fab.setVisibility(View.GONE);
            }
        });

        isAllFabsVisible = false;
    }
}
