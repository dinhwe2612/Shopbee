package com.example.shopbee.ui.signup;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.SignUpBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.common.base.BaseActivity;
import com.example.shopbee.ui.login.LoginActivity;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;
import com.saadahmedev.popupdialog.listener.StatusDialogActionListener;

public class SignUpActivity extends BaseActivity<SignUpBinding, SignUpViewModel> implements SignUpNavigator{
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.sign_up;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void signUpWithEmailAndPassWord() {
        SignUpBinding binding = getViewDataBinding();
        viewModel.signUp(binding.nameText.getText().toString(), binding.emailText.getText().toString(), binding.passwordText.getText().toString(), binding.confirmPasswordText.getText().toString());
    }

    @Override
    public void emptyBox() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Error")
                .setDescription("All fields are required!")
                .build(Dialog::dismiss)
                .show();
    }

    @Override
    public void passwordNotMatch() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Error")
                .setDescription("Password not match!")
                .build(Dialog::dismiss)
                .show();
    }

    @Override
    public void signUpSuccess() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading("Success")
                .setDescription("You have successfully signed up!")
                .build(new StatusDialogActionListener() {
                    @Override
                    public void onStatusActionClicked(Dialog dialog) {
                        navigateToLogin();
                    }
                })
                .show();
    }

    @Override
    public void signUpFailed(Exception exception) {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Error")
                .setDescription(exception.getMessage())
                .build(Dialog::dismiss)
                .show();
    }

    @Override
    public void navigateToLogin() {
        finish();
    }

    @Override
    public void loginWithFacebook() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Error")
                .setDescription("This feature is not available yet!")
                .build(Dialog::dismiss)
                .show();
    }

    @Override
    public void loginWithGoogle() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Error")
                .setDescription("This feature is not available yet!")
                .build(Dialog::dismiss)
                .show();
    }

}
