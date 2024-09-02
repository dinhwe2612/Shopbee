package com.example.shopbee.ui.checkout.payment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.PaymentBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.checkout.adapter.PaymentAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;

public class PaymentFragment extends BaseFragment<PaymentBinding, PaymentViewModel> implements PaymentNavigator, PaymentAdapter.Listener {
    private PaymentBinding binding;
    private UserResponse userResponse;
    private ListOrderResponse listOrderResponse;
    private PaymentAdapter paymentAdapter;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.payment;
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
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        int positionDef = -1;
        for (int i = 0; i < userResponse.getPayment().size(); i++) {
            if (userResponse.getPayment().get(i).getDef()){
                positionDef = i;
            }
        }
        paymentAdapter = new PaymentAdapter(this, userResponse.getPayment(), positionDef);
        recyclerView.setAdapter(paymentAdapter);
        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.layoutWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return binding.getRoot();
    }

    private void loadRealtimeData() {
        viewModel.getUserResponse().observeForever(response -> userResponse = response);
        viewModel.getOrderResponse().observeForever(response -> listOrderResponse = response);
    }

    @Override
    public void onClickItems(int position) {
        for (int i = 0; i < userResponse.getPayment().size(); i++) {
            userResponse.getPayment().get(i).setDef(false);
        }
        userResponse.getPayment().get(position).setDef(true);
    }
}
