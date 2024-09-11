package com.example.shopbee.ui.my_vouchers;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.MyVouchersBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.my_vouchers.adapter.MyVouchersAdapter;
import com.example.shopbee.ui.voucher.adapter.VoucherAdapter;

public class MyVouchersFragment extends BaseFragment<MyVouchersBinding, MyVouchersViewModel> implements MyVouchersNavigator {
    MyVouchersAdapter myVouchersAdapter = new MyVouchersAdapter();
    MyVouchersBinding binding;
    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_vouchers;
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
        binding.backbutton.setOnClickListener(v->{
            navigateUp();
        });
        setUpRecyclerView();
        observeData();
        observeLoading();
        viewModel.syncPromoCodes();
        return binding.getRoot();
    }
    public void animateLoading() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) binding.loading1.getBackground();
        if (!animationDrawable1.isRunning()) {
            animationDrawable1.start();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) binding.loading2.getBackground();
        if (!animationDrawable2.isRunning()) {
            animationDrawable2.start();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) binding.loading3.getBackground();
        if (!animationDrawable3.isRunning()) {
            animationDrawable3.start();
        }

        AnimationDrawable animationDrawable4 = (AnimationDrawable) binding.loading4.getBackground();
        if (!animationDrawable4.isRunning()) {
            animationDrawable4.start();
        }
    }

    public void stopLoadingAnimations() {
        AnimationDrawable animationDrawable1 = (AnimationDrawable) binding.loading1.getBackground();
        if (animationDrawable1.isRunning()) {
            animationDrawable1.stop();
        }

        AnimationDrawable animationDrawable2 = (AnimationDrawable) binding.loading2.getBackground();
        if (animationDrawable2.isRunning()) {
            animationDrawable2.stop();
        }

        AnimationDrawable animationDrawable3 = (AnimationDrawable) binding.loading3.getBackground();
        if (animationDrawable3.isRunning()) {
            animationDrawable3.stop();
        }

        AnimationDrawable animationDrawable4 = (AnimationDrawable) binding.loading4.getBackground();
        if (animationDrawable4.isRunning()) {
            animationDrawable4.stop();
        }
    }
    public void observeLoading() {
        viewModel.getInProgress().observe(getViewLifecycleOwner(), inProgress -> {
            if (inProgress) {
                binding.loading.setVisibility(View.VISIBLE);
                animateLoading();
            } else {
                stopLoadingAnimations();
                binding.loading.setVisibility(View.GONE);
                setUpRecyclerView();
            }
        });
    }
    public void observeData() {
        viewModel.getPromoCodeResponseList().observe(getViewLifecycleOwner(), list -> {
            myVouchersAdapter.setPromoCodeResponseList(list);
            myVouchersAdapter.notifyDataSetChanged();
        });
    }
    public void setUpRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(myVouchersAdapter);
    }

    @Override
    public void navigateUp() {
        NavHostFragment.findNavController(this).navigateUp();
    }
}
