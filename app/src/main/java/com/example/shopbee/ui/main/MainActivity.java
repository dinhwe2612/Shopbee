package com.example.shopbee.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.impl.bottombar.BottomBarView;
import com.example.shopbee.databinding.ActivityMainBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator {
    ActivityMainBinding binding;
    BottomBarView bottomBarView;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}