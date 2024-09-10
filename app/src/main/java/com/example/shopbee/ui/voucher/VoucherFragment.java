package com.example.shopbee.ui.voucher;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.VoucherBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.promoCode.PromoCodeDialog;
import com.example.shopbee.ui.voucher.adapter.VoucherAdapter;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.util.ArrayList;
import java.util.List;

public class VoucherFragment extends BaseFragment<VoucherBinding, VoucherViewModel> implements VoucherNavigator, VoucherAdapter.Listener{
    private VoucherBinding binding;
    private UserResponse userResponse;
    private MutableLiveData<PromoCodeResponse> promoCodeResponse = new MutableLiveData<>();
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.voucher;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = VoucherBinding.inflate(LayoutInflater.from(getContext()));
        viewModel.syncPromoCodes();
        RecyclerView shopbeeRecyclerView = binding.shopbeeRecyclerView;
        RecyclerView freeshipRecyclerView = binding.freeshipRecyclerView;
        RecyclerView newUserRecyclerView = binding.newbieRecyclerView;

        shopbeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        freeshipRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newUserRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewModel.getPromoCodes().observe(getViewLifecycleOwner(), result -> {
            List<PromoCodeResponse> shopbeeList = new ArrayList<>();
            List<PromoCodeResponse> freeshipList = new ArrayList<>();
            List<PromoCodeResponse> newUserList = new ArrayList<>();
            for (PromoCodeResponse promoCodeResponse : result) {
                switch (promoCodeResponse.getName()){
                    case "shopbee":
                        shopbeeList.add(promoCodeResponse);
                        break;
                    case "freeship":
                        freeshipList.add(promoCodeResponse);
                        break;
                    case "newbie":
                        newUserList.add(promoCodeResponse);
                        break;
                }
            }
            if  (userResponse != null){
                VoucherAdapter shopbeeAdapter = new VoucherAdapter(this, shopbeeList, userResponse.getEmail());
                VoucherAdapter freeshipAdapter = new VoucherAdapter(this, freeshipList, userResponse.getEmail());
                VoucherAdapter newUserAdapter = new VoucherAdapter(this, newUserList, userResponse.getEmail());
                shopbeeRecyclerView.setAdapter(shopbeeAdapter);
                freeshipRecyclerView.setAdapter(freeshipAdapter);
                newUserRecyclerView.setAdapter(newUserAdapter);
            } else {
                VoucherAdapter shopbeeAdapter = new VoucherAdapter(this, shopbeeList, "");
                VoucherAdapter freeshipAdapter = new VoucherAdapter(this, freeshipList, "");
                VoucherAdapter newUserAdapter = new VoucherAdapter(this, newUserList, "");
                shopbeeRecyclerView.setAdapter(shopbeeAdapter);
                freeshipRecyclerView.setAdapter(freeshipAdapter);
                newUserRecyclerView.setAdapter(newUserAdapter);
            }
        });
        binding.myVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigate to my voucher
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPreviousFragment();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onSaveVoucher(int position) {
        if (userResponse == null){
            PopupDialog.getInstance(getContext())
                    .standardDialogBuilder()
                    .createStandardDialog()
                    .setHeading("Login")
                    .setDescription("Login to save voucher and get discount")
                    .setIcon(R.drawable.login_icon)
                    .build(new StandardDialogActionListener() {
                        @Override
                        public void onPositiveButtonClicked(Dialog dialog) {
                            //Move to login
                        }

                        @Override
                        public void onNegativeButtonClicked(Dialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            // updatePromote code
        }
    }

    @Override
    public void backToPreviousFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }
}
