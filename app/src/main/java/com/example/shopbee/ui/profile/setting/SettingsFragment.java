package com.example.shopbee.ui.profile.setting;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.SettingsBinding;
import com.example.shopbee.di.component.FragmentComponent;

import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.changeCountry.changeCountryEvent;
import com.example.shopbee.ui.profile.adapter.ProfileAdapter;
import com.example.shopbee.ui.shop.categories.CategoriesHashMap;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SettingsFragment extends BaseFragment<SettingsBinding, SettingsViewModel> implements SettingsNavigator, DialogsManager.Listener {
    private SettingsBinding binding;
    private List<CountryRespone> listCountry;
    private UserResponse userResponse;
    private ListOrderResponse listOrderResponse;
    @Inject
    DialogsManager dialogsManager;
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
    public FragmentType getFragmentType() {
        return FragmentType.SELECT_PROFILE_ICON;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUpCountryItems();
        loadRealtimeData();

        binding.fullnameText.setText(userResponse.getFull_name());
        binding.DOBText.setText(userResponse.getDob());
        binding.passwordText.setText("123456789");
        binding.countryText.setText(userResponse.getCountry());
        int index = retrievePosition(userResponse.getCountry());
        Glide.with(this)
                .load(listCountry.get(index).getFlagPngUrl())// Optional error image
                .into(binding.countryImage);
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
                dialogsManager.changePasswordDialog("123456789", userResponse.getFull_name(), userResponse.getEmail());
            }
        });
        binding.changeCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsManager.changeCountryDialog(userResponse.getCountry(), listCountry);

            }
        });
        binding.fullnameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()){
                    binding.hintChangeName.setHintTextColor(getResources().getColor(R.color.red));
                    binding.changeNameLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else {
                    binding.hintChangeName.setHintTextColor(getResources().getColor(R.color.gray));
                    binding.changeNameLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle);
                    userResponse.setFull_name(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updateUserFirebase();
            }
        });
    }
    void settingTheme(){
        SharedPreferences sharedPref = getActivity().getApplicationContext().getSharedPreferences("THEME", Context.MODE_PRIVATE);
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
    void setUpCountryItems(){
        listCountry = new ArrayList<>();
        try {
            InputStream inputStream = getContext().getResources().openRawResource(R.raw.countries_info);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 5) {
                    String name = parts[0];
                    String code = parts[1];
                    String currency = parts[2];
                    String flagPngUrl = parts[3];
                    String flagSvgUrl = parts[4];
                    listCountry.add(new CountryRespone(name, code, currency, flagPngUrl, flagSvgUrl));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(listCountry, new Comparator<CountryRespone>() {
            @Override
            public int compare(CountryRespone c1, CountryRespone c2) {
                return c1.getCode().compareTo(c2.getCode());
            }
        });
    }
    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof changeCountryEvent) {
            Toast.makeText(getContext(), "Change country successfully", Toast.LENGTH_SHORT).show();
            changeCountryEvent mChangeCountryEvent = (changeCountryEvent) event;
            Log.d("TAG", "onDialogEvent: " + mChangeCountryEvent.getNewCountry());
            binding.countryText.setText(mChangeCountryEvent.getNewCountry() + " (" + mChangeCountryEvent.getNewCode() + ")");
            Disposable d = (Observable.fromCallable(() -> {
                    FutureTarget<Bitmap> futureTarget = Glide.with(this)
                            .asBitmap()
                            .load(mChangeCountryEvent.getPngUrl())
                            .submit();
                    return futureTarget.get();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    binding.countryImage.setImageBitmap(bitmap);
                }, throwable -> {
                    Log.e("Exception", throwable.getMessage());
                })
            );
            userResponse.setCountry(mChangeCountryEvent.getNewCountry());
            viewModel.updateUserFirebase();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        dialogsManager.registerListener(this);
    }
    @Override
    public void onStop() {
        super.onStop();
        dialogsManager.unregisterListener(this);
    }
    void loadRealtimeData(){
        viewModel.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
                userResponse = response;
            }
        });
        viewModel.getOrderResponse().observeForever(new Observer<ListOrderResponse>() {
            @Override
            public void onChanged(ListOrderResponse responses) {
                listOrderResponse = responses;
            }
        });
    }
    public int retrievePosition(String countryName){
        try {
            for (int i = 0; i < listCountry.size(); i++) {
                if (listCountry.get(i).getName().equals(countryName)) {
                    return i;
                }
            }
            throw new Exception("Country name not found: " + countryName);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            return -1;
        }
    }
}

