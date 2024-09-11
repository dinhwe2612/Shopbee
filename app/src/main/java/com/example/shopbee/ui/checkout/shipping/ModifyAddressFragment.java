package com.example.shopbee.ui.checkout.shipping;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.shopbee.R;
import com.example.shopbee.data.model.api.AddressResponse;
import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.databinding.ModifyAddressBinding;
import com.example.shopbee.di.component.FragmentComponent;
import com.example.shopbee.ui.checkout.adapter.PaymentAdapter;
import com.example.shopbee.ui.common.base.BaseFragment;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.changeCountry.changeCountryEvent;

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

public class ModifyAddressFragment extends BaseFragment<ModifyAddressBinding, ModifyAddressViewModel> implements DialogsManager.Listener, ModifyAddressNavigator{
    private ModifyAddressBinding binding;
    private List<CountryRespone> listCountry;
    private AddressResponse addressResponse;
    private UserResponse userResponse;

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.HIDE_BOTTOM_BAR;
    }

    @Inject
    DialogsManager dialogsManager;
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public int getLayoutId() {
        return R.layout.modify_address;
    }

    @Override
    public void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
        viewModel.setNavigator(this);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = getViewDataBinding();
        loadRealtimeDate();
        setUpCountryItems();
        String modify = getArguments().getString("modify");
        int position = getArguments().getInt("position");
        addressResponse = new AddressResponse();
        if (modify.equals("edit")){
            String name = getArguments().getString("name");
            String address = getArguments().getString("address");
            String city = getArguments().getString("city");
            String state = getArguments().getString("state");
            String zip = getArguments().getString("zip");
            String country = getArguments().getString("country");
            int index = retrievePosition(country);
            binding.name.setText(name);
            binding.address.setText(address);
            binding.city.setText(city);
            binding.state.setText(state);
            binding.country.setText(country);
            binding.zip.setText(zip);
            Glide.with(this)
                    .load(listCountry.get(index).getFlagPngUrl())
                    .into(binding.countryImage);
            addressResponse = new AddressResponse(address, city, state, country, zip, false, name);
        } else {
            addressResponse.setCountry("");
        }
        handleStateOfInput(binding.name, binding.nameHint, binding.nameLayout);
        handleStateOfInput(binding.address, binding.addressHint, binding.addressLayout);
        handleStateOfInput(binding.city, binding.cityHint, binding.cityLayout);
        handleStateOfInput(binding.state, binding.stateHint, binding.stateLayout);
        handleStateOfInput(binding.zip, binding.zipHint, binding.zipLayout);

        binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsManager.changeCountryDialog(addressResponse.getCountry(), listCountry);
            }
        });
        binding.saveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.name.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter full name", Toast.LENGTH_SHORT).show();
                    binding.name.setHintTextColor(getResources().getColor(R.color.red));
                    binding.nameLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.address.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter address", Toast.LENGTH_SHORT).show();
                    binding.address.setHintTextColor(getResources().getColor(R.color.red));
                    binding.addressLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.city.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter city", Toast.LENGTH_SHORT).show();
                    binding.city.setHintTextColor(getResources().getColor(R.color.red));
                    binding.cityLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.state.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter state", Toast.LENGTH_SHORT).show();
                    binding.state.setHintTextColor(getResources().getColor(R.color.red));
                    binding.stateLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.zip.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter zip code", Toast.LENGTH_SHORT).show();
                    binding.zip.setHintTextColor(getResources().getColor(R.color.red));
                    binding.zipLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (addressResponse.getCountry().isEmpty()) {
                    Toast.makeText(getContext(), "Please select country", Toast.LENGTH_SHORT).show();
                    binding.country.setHintTextColor(getResources().getColor(R.color.red));
                    binding.countryLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else {
                    Toast.makeText(getContext(), "Save address successfully", Toast.LENGTH_SHORT).show();
                    if (modify.equals("edit")) {
                        userResponse.getAddress().get(position).setAddress(addressResponse.getAddress());
                        userResponse.getAddress().get(position).setCity(addressResponse.getCity());
                        userResponse.getAddress().get(position).setState(addressResponse.getState());
                        userResponse.getAddress().get(position).setZip_code(addressResponse.getZip_code());
                        userResponse.getAddress().get(position).setCountry(addressResponse.getCountry());
                        userResponse.getAddress().get(position).setName(addressResponse.getName());
                    } else {
                        if (userResponse.getAddress() == null){
                            userResponse.setAddress(new ArrayList<>());
                        }
                        addressResponse.setName(binding.name.getText().toString());
                        addressResponse.setAddress(binding.address.getText().toString());
                        addressResponse.setCity(binding.city.getText().toString());
                        addressResponse.setState(binding.state.getText().toString());
                        addressResponse.setZip_code(binding.zip.getText().toString());
                        addressResponse.setDef(false);
                        
                        userResponse.getAddress().add(addressResponse);
                    }
                    viewModel.updateUserFirebase();
                    backToShippingFragment();
                }
            }
        });
        binding.buttonBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToShippingFragment();
            }
        });
        return binding.getRoot();
    }
    private void loadRealtimeDate() {
        viewModel.getUserResponse().observeForever(response -> userResponse = response);
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
    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof changeCountryEvent) {
            Toast.makeText(getContext(), "Change country successfully", Toast.LENGTH_SHORT).show();
            changeCountryEvent mChangeCountryEvent = (changeCountryEvent) event;
            Log.d("TAG", "onDialogEvent: " + mChangeCountryEvent.getNewCountry());
            binding.country.setText(mChangeCountryEvent.getNewCountry());
            binding.country.setTextColor(getResources().getColor(R.color.black_light_theme));
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
            addressResponse.setCountry(mChangeCountryEvent.getNewCountry());
        }

    }
    private void handleStateOfInput(EditText editText, TextView hint, LinearLayout layout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().isEmpty()){
                    hint.setVisibility(View.GONE);
                } else {
                    hint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setHintTextColor(getResources().getColor(R.color.gray));
                hint.setTextColor(getResources().getColor(R.color.gray));
                layout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle);
                if (s.toString().isEmpty()){
                    hint.setVisibility(View.GONE);
                } else {
                    hint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    hint.setVisibility(View.GONE);
                } else {
                    hint.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    @Override
    public void backToShippingFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigateUp();
    }
}
