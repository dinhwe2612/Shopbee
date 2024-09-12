package com.example.shopbee.ui.checkout.shipping;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
import com.example.shopbee.ui.checkout.adapter.ShippingAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

public class ShippingFragment extends BaseFragment<ShippingBinding, ShippingViewModel> implements ShippingNavigator, ShippingAdapter.Listener {
    private ShippingBinding binding;
    private UserResponse userResponse;
    private ShippingAdapter shippingAdapter;
    private CardView fab, write_fab, map_fab;
    private Boolean isAllFabsVisible;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private ActivityResultLauncher<String> requestPermissionLauncher;
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
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
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
        if (userResponse.getAddress().isEmpty()){
            binding.emptyShipping.setVisibility(View.VISIBLE);
            binding.recyclerViewShipping.setVisibility(View.GONE);
        } else {
            binding.emptyShipping.setVisibility(View.GONE);
            binding.recyclerViewShipping.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < userResponse.getAddress().size(); i++) {
            if (userResponse.getAddress().get(i).getDef()){
                positionDef = i;
            }
        }
        shippingAdapter = new ShippingAdapter(this, userResponse.getAddress(), positionDef);
        recyclerView.setAdapter(shippingAdapter);

        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToCheckout();
            }
        });
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                addNewAddressByMap(userResponse.getCountry());
            } else {
                Toast.makeText(getActivity(), "Location permission is required to access the map.", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }

    private void loadRealtimeData() {
        viewModel.getUserResponse().observeForever(response -> userResponse = response);
    }

    @Override
    public void onClickItems(int position) {
        if (position == -1){
            for (int i = 0; i < userResponse.getAddress().size(); i++) {
                userResponse.getAddress().get(i).setDef(false);
            }
        } else {
            for (int i = 0; i < userResponse.getAddress().size(); i++) {
                userResponse.getAddress().get(i).setDef(false);
            }
            userResponse.getAddress().get(position).setDef(true);
        }
        viewModel.updateUserFirebase();
    }

    @Override
    public void onEditItems(int position) {
        AddressResponse addressResponse = userResponse.getAddress().get(position);
        editAddress(addressResponse, position);
    }

    @Override
    public void onClickDeleteItems(int position) {
        PopupDialog.getInstance(getContext())
                .standardDialogBuilder()
                .createStandardDialog()
                .setHeading("Delete an address")
                .setDescription("Are you sure you want to delete this address?")
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setPositiveButtonTextColor(R.color.white)
                .setIcon(R.drawable.cancel_image)
                .build(new StandardDialogActionListener() {
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        userResponse.getAddress().remove(position);
                        shippingAdapter.notifyItemChanged(position);
                        shippingAdapter.notifyItemRangeChanged(position, userResponse.getAddress().size());
                        viewModel.updateUserFirebase();
                        dialog.dismiss();
                    }
                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
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
        bundle.putString("name", addressResponse.getName());
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

    @Override
    public void addNewAddressByMap(String country_def) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putString("country_def", country_def);
        navController.navigate(R.id.modifyByMapFragment, bundle);
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
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    addNewAddressByMap(userResponse.getCountry());
                }
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
