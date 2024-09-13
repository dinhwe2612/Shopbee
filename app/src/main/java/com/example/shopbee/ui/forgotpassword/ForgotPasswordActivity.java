package com.example.shopbee.ui.forgotpassword;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ForgotPasswordBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.common.base.BaseActivity;
import com.saadahmedev.popupdialog.PopupDialog;

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

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void sendEmail() {
        String email = binding.emailText.getText().toString();
        // check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailText.setError("Please enter a valid email address");
            return;
        }
        viewModel.sentEmail(email);
    }

    @Override
    public void sentEmailSuccess() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading("Send Email Successfully")
                .setDescription("Please check email to reset password")
                .build(Dialog::dismiss)
                .show();
    }

    @Override
    public void sentEmailFailed(String message) {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Uh-Oh")
                .setDescription("Email not found.")
                .build(Dialog::dismiss)
                .show();
    }
}
