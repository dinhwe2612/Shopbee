package com.example.shopbee.ui.login;

import android.content.Context;
import android.content.Intent;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.ActivityLoginBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    ActivityLoginBinding activityLoginBinding;
    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
