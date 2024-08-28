package com.example.shopbee.ui.common.dialogs.changeCountry;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.databinding.ChangeCountryBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.profile.adapter.CountryAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import javax.inject.Inject;

public class changeCountryDialog extends DialogFragment implements CountryAdapter.Listener{
    public static String ARG_OLD_COUNTRY = "old_country";
    private ChangeCountryBinding binding;
    private List<CountryRespone> countries;
    private int positionChange;
    private CountryAdapter adapter;
    private DialogsManager dialogsManager;

    public static changeCountryDialog newInstance(DialogsManager dialogsManager, String old_country, List<CountryRespone> listCountry) {
        changeCountryDialog dialog = new changeCountryDialog();
        dialog.countries = listCountry;
        dialog.dialogsManager = dialogsManager;
        Bundle args = new Bundle();
        args.putString(ARG_OLD_COUNTRY, old_country);
        dialog.setArguments(args);
        return dialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        binding = ChangeCountryBinding.inflate(getLayoutInflater());

        String old_country = getArguments().getString(ARG_OLD_COUNTRY);
        Log.d("TAG", "onCreateDialog: " + old_country);
        positionChange = retrievePosition(old_country);

        RecyclerView recyclerView = binding.recyclerViewCountry;
        adapter = new CountryAdapter(this, countries, positionChange);
        recyclerView.setAdapter(adapter);

        binding.saveCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCountryEvent event = new changeCountryEvent(getCountryName(), getCountryCode(), getCountryPngUrl(), getCountryPosition());
                dialogsManager.postEvent(event);
                dismiss();
            }
        });
        BottomSheetDialog dialog =  new BottomSheetDialog(requireContext());
        dialog.setContentView(binding.getRoot());
        return dialog;
    }
    @Override
    public void onClickItems(int position) {
        this.positionChange = position;
        Toast.makeText(getContext(), countries.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
    public int getCountryPosition(){
        return positionChange;
    }
    public String getCountryName(){
        return countries.get(positionChange).getName();
    }
    public String getCountryCode(){
        return countries.get(positionChange).getCode();
    }
    public String getCountryPngUrl(){
        return countries.get(positionChange).getFlagPngUrl();
    }
    int retrievePosition(String countryName){
        try {
            for (int i = 0; i < countries.size(); i++) {
                if (countries.get(i).getName().equals(countryName)) {
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
