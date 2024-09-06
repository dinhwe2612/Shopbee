package com.example.shopbee.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
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
    public BottomBarUserReactionImplementation getBottomBar() {
        return bottomBar;
    }
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
        int fromFragmentId = NavGraph.findStartDestination(navController.getGraph()).getId();
        switch (position) {
            case 0:
                navigateWithOptions(fromFragmentId, R.id.homeFragment, navController);
                break;
            case 1:
                navigateWithOptions(fromFragmentId, R.id.shopFragment, navController);
                break;
            case 2:
                navigateWithOptions(fromFragmentId, R.id.bagFragment, navController);
                break;
            case 3:
                navigateWithOptions(fromFragmentId, R.id.favoritesFragment, navController);
                break;
            case 4:
                navigateWithOptions(fromFragmentId, R.id.profileFragment, navController);
                break;
        }
    }

    public void navigateWithOptions(int fromFragmentId, int toFragmentId, NavController navController) {
        // Set up navigation options
        NavOptions navOptions = new NavOptions.Builder()
                .setLaunchSingleTop(true)  // Ensure we do not recreate the fragment if it's already on top
                .setRestoreState(true)     // Restore state of the destination fragment
                .setPopUpTo(fromFragmentId, true, true)  // Pop up to the fromFragmentId and remove all fragments above it
                .build();

        // Perform the navigation
        navController.navigate(toFragmentId, null, navOptions);

        Log.d("TAG", "navigateWithOptions: Navigated to " + toFragmentId + " from " + fromFragmentId);
    }
}