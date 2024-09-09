package com.example.shopbee.ui.voucher;

import com.example.shopbee.R;
import com.example.shopbee.databinding.VoucherBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.common.base.BaseFragment;

public class VoucherFragment extends BaseFragment<VoucherBinding, VoucherViewModel> {
    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.voucher;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {

    }
}
