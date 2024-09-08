package com.example.shopbee.ui.common.dialogs.loadingdialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.databinding.LoadingDialogBinding;

public class LoadingDialog extends DialogFragment {
    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LoadingDialogBinding binding = LoadingDialogBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }
}
