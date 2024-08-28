package com.example.shopbee.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.ActivityMainBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.component.bottombar.BottomBarUserReactionImplementation;
import com.example.shopbee.ui.component.bottombar.BottomBarUserReactionListener;
import com.example.shopbee.ui.common.base.BaseActivity;

import javax.inject.Inject;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements MainNavigator
                , BottomBarUserReactionListener {
    private NavController navController;
    ActivityMainBinding binding;
    @Inject
    BottomBarUserReactionImplementation bottomBar;
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
        bottomBar.bindView(binding.bottomBar, this);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(binding.fragmentContainer.getId());
        navController = navHostFragment.getNavController();
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                navController.navigate(R.id.homeFragment);
                break;
            case 1:
                navController.navigate(R.id.shopFragment);
                break;
            case 2:
                navController.navigate(R.id.bagFragment);
                break;
            case 3:
                navController.navigate(R.id.favoritesFragment);
                break;
            case 4:
                navController.navigate(R.id.profileFragment);
                break;
        }
    }
}