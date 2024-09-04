package com.example.shopbee.ui.common.dialogs.promoCode;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.data.model.filter.SortByChoice;
import com.example.shopbee.databinding.BottomSheetLayoutPromoCodeBinding;
import com.example.shopbee.databinding.BottomSheetLayoutSortByBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.promoCode.adapter.PromoCodeAdapter;
import com.example.shopbee.ui.common.dialogs.sortbydialog.DialogAdapter;
import com.example.shopbee.ui.common.dialogs.sortbydialog.SortByDialog;
import com.example.shopbee.ui.common.dialogs.sortbydialog.SortByEvent;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PromoCodeDialog extends BottomSheetDialogFragment implements  PromoCodeAdapter.OnItemClick{
    public interface onCollectVoucherListener {
        void onCollectVoucher();
    }
    public void setOnCollectVoucherListener(onCollectVoucherListener onCollectVoucherListener) {
        this.onCollectVoucherListener = onCollectVoucherListener;
    }
    private onCollectVoucherListener onCollectVoucherListener;
    private List<PromoCodeResponse> originalPromoCodeList = new ArrayList<>();
    private List<PromoCodeResponse> filteredPromoCodeList = new ArrayList<>();

    public void setPromoCodeResponseList(List<PromoCodeResponse> promoCodeResponseList) {
        this.promoCodeResponseList = promoCodeResponseList;
        this.originalPromoCodeList = new ArrayList<>(promoCodeResponseList); // Keep the original list
        this.filteredPromoCodeList = new ArrayList<>(promoCodeResponseList); // Initialize with the full list
    }
    List<PromoCodeResponse> promoCodeResponseList = new ArrayList<>();

    String promoCode;
    PromoCodeResponse promoCodeResponse;
    PromoCodeAdapter promoCodeAdapter = new PromoCodeAdapter();
    DialogsManager dialogsManager;
    public void setDialogsManager(DialogsManager dialogsManager) {
        this.dialogsManager = dialogsManager;
    }

    public void setPromoCodeResponse(PromoCodeResponse promoCodeResponse) {
        this.promoCodeResponse = promoCodeResponse;
    }

    public void setPromoCodeAdapter(PromoCodeAdapter promoCodeAdapter) {
        this.promoCodeAdapter = promoCodeAdapter;
    }
    BottomSheetDialog dialog;
    BottomSheetLayoutPromoCodeBinding binding;
    BottomSheetBehavior<View> bottomSheetBehavior;
    public static PromoCodeDialog newInstance(DialogsManager dialogsManager) {
        PromoCodeDialog dialog = new PromoCodeDialog();
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
        binding = BottomSheetLayoutPromoCodeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(promoCodeAdapter);
        promoCodeAdapter.setPromoCodeList(promoCodeResponseList);
        promoCodeAdapter.setCurrentItem(promoCodeResponse);
        promoCodeAdapter.setOnItemClick(this);
        binding.collectVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCollectVoucherListener.onCollectVoucher();
            }
        });
        binding.promoCodeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text is changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterPromoCodes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text is changed
            }
        });

    }
    private void filterPromoCodes(String query) {
        if (query.isEmpty()) {
            filteredPromoCodeList = new ArrayList<>(originalPromoCodeList);
        } else {
            filteredPromoCodeList = originalPromoCodeList.stream()
                    .filter(promo -> promo.getCode().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        promoCodeAdapter.setPromoCodeList(filteredPromoCodeList);
        promoCodeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(PromoCodeResponse promoCodeResponse) {
        dialogsManager.postEvent(promoCodeResponse);
        dismiss();
    }
}
