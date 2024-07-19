package com.example.shopbee.ui.main;

import static io.reactivex.rxjava3.internal.schedulers.SchedulerPoolFactory.start;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopbee.BR;
import com.example.shopbee.R;
import com.example.shopbee.databinding.ActivityMainBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.base.BaseActivity;
import com.example.shopbee.utils.NetworkUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator {
    ActivityMainBinding binding;
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
        viewModel.setNavigator(this);
        viewModel.getCurrentlySearchProducts(NetworkUtils.createSearchQuery("Phone", "1", "US"));
        showLoading();
        viewModel.getProductNames().observe(this, names -> {
            if (names != null && !names.isEmpty()) {
                hideLoading();
                binding.textview.setText(names.toString());
            }
        });
    }

    @Override
    public void handleError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}