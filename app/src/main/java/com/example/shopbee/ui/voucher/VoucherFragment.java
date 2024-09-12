package com.example.shopbee.ui.voucher;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.GameBinding;
import com.example.shopbee.databinding.VoucherBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.promoCode.PromoCodeDialog;
import com.example.shopbee.ui.flappybee.GameActivity;
import com.example.shopbee.ui.main.MainActivity;
import com.example.shopbee.ui.voucher.adapter.VoucherAdapter;
import com.example.shopbee.ui.voucher.adapter.VoucherBannerAdapter;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VoucherFragment extends BaseFragment<VoucherBinding, VoucherViewModel> implements
        VoucherNavigator,
        VoucherAdapter.Listener,
        VoucherBannerAdapter.Listener{
    private VoucherBinding binding;
    private UserResponse userResponse;
    private List<PromoCodeResponse> shopbeeList;
    private List<PromoCodeResponse> freeshipList;
    private List<PromoCodeResponse> newUserList;
    private VoucherAdapter shopbeeAdapter;
    private VoucherAdapter freeshipAdapter;
    private VoucherAdapter newUserAdapter;

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
        viewModel.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
                userResponse = response;
            }
        });
        RecyclerView shopbeeRecyclerView = binding.shopbeeRecyclerView;
        RecyclerView freeshipRecyclerView = binding.freeshipRecyclerView;
        RecyclerView newUserRecyclerView = binding.newbieRecyclerView;
        binding.bannerRCV.setAdapter(new VoucherBannerAdapter(this));
        binding.bannerRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        new PagerSnapHelper().attachToRecyclerView(binding.bannerRCV);
        shopbeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        freeshipRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newUserRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewModel.getPromoCodes().observe(getViewLifecycleOwner(), result -> {
            shopbeeList = new ArrayList<>();
            freeshipList = new ArrayList<>();
            newUserList = new ArrayList<>();
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
                viewModel.syncPromoCodesOfUser();
                viewModel.getPromoCodeOfUser().observe(getViewLifecycleOwner(), listCodeUser -> {
                    shopbeeAdapter = new VoucherAdapter(this, shopbeeList, userResponse.getEmail(), listCodeUser);
                    freeshipAdapter = new VoucherAdapter(this, freeshipList, userResponse.getEmail(), listCodeUser);
                    newUserAdapter = new VoucherAdapter(this, newUserList, userResponse.getEmail(), listCodeUser);
                    shopbeeRecyclerView.setAdapter(shopbeeAdapter);
                    freeshipRecyclerView.setAdapter(freeshipAdapter);
                    newUserRecyclerView.setAdapter(newUserAdapter);
                });
            } else {
                shopbeeAdapter = new VoucherAdapter(this, shopbeeList, "", null);
                freeshipAdapter = new VoucherAdapter(this, freeshipList, "", null);
                newUserAdapter = new VoucherAdapter(this, newUserList, "", null);
                shopbeeRecyclerView.setAdapter(shopbeeAdapter);
                freeshipRecyclerView.setAdapter(freeshipAdapter);
                newUserRecyclerView.setAdapter(newUserAdapter);
            }
        });
        binding.myVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToMyVoucher();
            }
        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToPreviousFragment();
            }
        });
        binding.game.setOnClickListener(v->navigateToGameActivity());
        return binding.getRoot();
    }
    @Override
    public void backToPreviousFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }

    @Override
    public void navigateToMyVoucher() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.myVouchersFragment);
    }

    @Override
    public void navigateToGameActivity() {
        Intent intent = new Intent(requireContext(), GameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveVoucherFreeShip(int position) {
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
            PromoCodeResponse newPromoCode = freeshipList.get(position);
            viewModel.getCompositeDisposable().add(viewModel.getRepository().savePromoCode(newPromoCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success ->{
                                if (success){
                                    viewModel.getPromoCodeOfUser().getValue().add(newPromoCode);
                                    freeshipAdapter.notifyItemChanged(position, viewModel.getPromoCodeOfUser().getValue());
                                }
                            },
                            error ->{

                            })
            );
        }
    }

    @Override
    public void onSaveVoucherShopbee(int position) {
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
            PromoCodeResponse newPromoCode = shopbeeList.get(position);
            viewModel.getCompositeDisposable().add(viewModel.getRepository().savePromoCode(newPromoCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success ->{
                                if (success){
                                    viewModel.getPromoCodeOfUser().getValue().add(newPromoCode);
                                    shopbeeAdapter.notifyItemChanged(position, viewModel.getPromoCodeOfUser().getValue());
                                }
                            },
                            error ->{

                            })
            );
        }
    }

    @Override
    public void onSaveVoucherNewbie(int position) {
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
            PromoCodeResponse newPromoCode = newUserList.get(position);
            viewModel.getCompositeDisposable().add(viewModel.getRepository().savePromoCode(newPromoCode)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success ->{
                                if (success){
                                    viewModel.getPromoCodeOfUser().getValue().add(newPromoCode);
                                    newUserAdapter.notifyItemChanged(position, viewModel.getPromoCodeOfUser().getValue());
                                }
                            },
                            error ->{

                            })
            );

        }
    }

    @Override
    public void onBannerGameClick() {
        navigateToGameActivity();
    }
}
