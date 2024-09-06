package com.example.shopbee.ui.common.dialogs.morevariation;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.databinding.ChangeCountryBinding;
import com.example.shopbee.databinding.MoreVariationBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.changeCountry.changeCountryDialog;
import com.example.shopbee.ui.common.dialogs.morevariation.adapter.variationAdapter;


import java.util.List;

public class moreVariationDialog extends DialogFragment {
    private List<Pair<String, String>> variations;
    private OrderDetailResponse orderDetailResponse;
    private DialogsManager dialogsManager;
    private variationAdapter adapter;
    private MoreVariationBinding binding;

    public static moreVariationDialog newInstance(DialogsManager dialogsManager, OrderDetailResponse orderDetailResponse) {
        moreVariationDialog dialog = new moreVariationDialog();
        Bundle args = new Bundle();
        args.putParcelable("orderDetailResponse", orderDetailResponse);
        dialog.setArguments(args);
        return dialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        binding = MoreVariationBinding.inflate(getLayoutInflater());
        orderDetailResponse = getArguments().getParcelable("orderDetailResponse");
        variations = orderDetailResponse.getVariation();

        Glide.with(getContext())
                .load(orderDetailResponse.getUrlImage())
                .into(binding.imageProduct);
        binding.name.setText(orderDetailResponse.getProduct_name());
        binding.price.setText(orderDetailResponse.getPrice());
        binding.quantity.setText(String.valueOf(orderDetailResponse.getQuantity()));

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new variationAdapter(variations);
        recyclerView.setAdapter(adapter);

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(binding.getRoot());
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        return dialog;
    }
}
