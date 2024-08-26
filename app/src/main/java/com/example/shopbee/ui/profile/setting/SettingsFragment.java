package com.example.shopbee.ui.profile.setting;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.SettingsBinding;
import com.example.shopbee.di.component.FragmentComponent;

import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.profile.adapter.ProfileAdapter;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;

public class SettingsFragment extends BaseFragment<SettingsBinding, SettingsViewModel> implements SettingsNavigator {
    SettingsBinding binding;
    @Override
    public int getBindingVariable() {return BR.vm;}
    @Override
    public int getLayoutId() {return R.layout.settings;}
    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();

        binding.fullnameText.setText("Nguyen Minh Luan");
        binding.DOBText.setText("19/07/2001");
        binding.passwordText.setText("123456789");
        binding.countryText.setText("Viet Nam (VN)");
        settingTheme();

        NavController navController = NavHostFragment.findNavController(this);
        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    void settingTheme(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPref.getBoolean("DARK_MODE", false);
        if (isDarkMode){
            binding.dayNightSwitch.setOn(false);
        } else binding.dayNightSwitch.setOn(true);

        binding.dayNightSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                Log.d("TAG", "onSwitched: " + isOn);
                if (!isOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("DARK_MODE", !isOn);
                editor.apply();
            }
        });
    }
}

