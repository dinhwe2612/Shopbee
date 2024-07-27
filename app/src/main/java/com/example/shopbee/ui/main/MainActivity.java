package com.example.shopbee.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.ActivityMainBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.impl.bottombar.BottomBarUserReactionImplementation;
import com.example.shopbee.impl.bottombar.BottomBarUserReactionListener;
import com.example.shopbee.ui.base.BaseActivity;
import com.example.shopbee.ui.login.LoginActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator
                , BottomBarUserReactionListener {
    ActivityMainBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

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
        binding = getViewDataBinding();
        BottomBarUserReactionImplementation bottomBar = new BottomBarUserReactionImplementation();
        bottomBar.bindView(binding.bottomBar, this);
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "Shop", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "Bag", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}