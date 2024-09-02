package com.example.shopbee.ui.checkout.payment;

import androidx.databinding.library.baseAdapters.BR;

import com.example.shopbee.R;
import com.example.shopbee.databinding.PaymentBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;

public class PaymentFragment extends BaseFragment<PaymentBinding, PaymentViewModel> implements PaymentNavigator {
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
}
