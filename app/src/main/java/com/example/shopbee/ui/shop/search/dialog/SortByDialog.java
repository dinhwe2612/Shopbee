package com.example.shopbee.ui.shop.search.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shopbee.R;
import com.example.shopbee.data.model.filter.SortByChoice;
import com.example.shopbee.databinding.BottomSheetLayoutSortByBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

public class SortByDialog extends BottomSheetDialogFragment {
    DialogAdapter dialogAdapter;
    DialogsManager dialogsManager;
    public void setDialogsManager(DialogsManager dialogsManager) {
        this.dialogsManager = dialogsManager;
    }
    BottomSheetDialog dialog;
    BottomSheetLayoutSortByBinding binding;
    BottomSheetBehavior<View> bottomSheetBehavior;
    public static SortByDialog newInstance(DialogsManager dialogsManager) {
        SortByDialog dialog = new SortByDialog();
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
        binding = BottomSheetLayoutSortByBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void notifyDatasetChanged() {
        dialogAdapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        if (dialogAdapter == null) {
            dialogAdapter = new DialogAdapter();
            dialogAdapter.setOnSortByChoiceSelectedListener(new DialogAdapter.OnSortByChoiceSelectedListener() {
                @Override
                public void onSortByChoiceSelected(SortByChoice sortByChoice) {
                    SortByEvent sortByEvent = new SortByEvent(sortByChoice);
                    dialogsManager.postEvent(sortByEvent);
                    dismiss();
                }
            });
        }
        binding.recyclerView.setAdapter(dialogAdapter);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
//    @Override
//    public int getTheme() {
//        return R.style.SortOptionUnselected; // Replace with your custom theme
//    }
}
