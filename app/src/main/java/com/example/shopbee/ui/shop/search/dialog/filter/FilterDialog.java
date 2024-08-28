package com.example.shopbee.ui.shop.search.dialog.filter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.data.model.filter.ProductCondition;
import com.example.shopbee.data.model.filter.ProductCountry;
import com.example.shopbee.data.model.filter.SortByChoice;
import com.example.shopbee.databinding.BottomSheetLayoutSortByBinding;
import com.example.shopbee.databinding.FiltersBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.shop.search.dialog.sort.SortByDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterDialog extends BottomSheetDialogFragment {
    DialogsManager dialogsManager;
    public void setDialogsManager(DialogsManager dialogsManager) {
        this.dialogsManager = dialogsManager;
    }
    BottomSheetDialog dialog;
    FiltersBinding binding;
    BottomSheetBehavior<View> bottomSheetBehavior;
    Filter filter;
    public FilterDialog(Filter filter) {
        super();
        this.filter = filter;
    }
    public static FilterDialog newInstance(DialogsManager dialogsManager, Filter filter) {
        FilterDialog dialog = new FilterDialog(filter);
        dialog.setDialogsManager(dialogsManager);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FiltersBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.draggableCircle.setMinPrice(filter.getMin_price());
        binding.draggableCircle.setMaxPrice(filter.getMax_price());
        binding.recyclerView2.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        ProductConditionAdapter productConditionAdapter = new ProductConditionAdapter(filter.getProductCondition());
        binding.recyclerView2.setAdapter(productConditionAdapter);
        Animation clickAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.button_click_animation);
        binding.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView6.startAnimation(clickAnimation);
                dismiss();
            }
        });
        binding.textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView7.startAnimation(clickAnimation);
                filter.setMin_price(binding.draggableCircle.getMinPrice());
                filter.setMax_price(binding.draggableCircle.getMaxPrice());
                filter.setProductCondition(productConditionAdapter.getProductCondition());
                dialogsManager.postEvent(new FilterEvent(filter));
                dismiss();
            }
        });
//        binding.recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));
//        binding.recyclerView.setAdapter(new ProductCountryAdapter(filter.getProductCountry()));
    }
}

