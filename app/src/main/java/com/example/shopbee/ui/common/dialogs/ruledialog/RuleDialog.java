package com.example.shopbee.ui.common.dialogs.ruledialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.RuleDialogBinding;

public class RuleDialog extends DialogFragment {
    public static RuleDialog newInstance() {
        RuleDialog dialog = new RuleDialog();
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        RuleDialogBinding binding = RuleDialogBinding.inflate(getLayoutInflater());
        binding.close.setOnClickListener(v -> dismiss());
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 400, getContext().getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getContext().getResources().getDisplayMetrics()));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        return dialog;
    }
}
