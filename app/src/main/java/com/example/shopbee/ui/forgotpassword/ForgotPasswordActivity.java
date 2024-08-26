package com.example.shopbee.ui.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ForgotPasswordBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.base.BaseActivity;
import com.example.shopbee.ui.login.LoginActivity;

import java.util.Map;

public class ForgotPasswordActivity extends BaseActivity<ForgotPasswordBinding, ForgotPasswordViewModel>
        implements ForgotPasswordNavigator{
    ForgotPasswordBinding binding;
    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }
    @Override
    public int getBindingVariable() {
            return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.forgot_password;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        binding = getViewDataBinding();
    }

    @Override
    public void sendEmail() {
        viewModel.sentEmail(binding.emailText.toString());
    }

    @Override
    public void sentEmailSuccess() {

    }

    @Override
    public void sentEmailFailed(String message) {

    }
}
