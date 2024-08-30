package com.example.shopbee.ui.common.dialogs.optiondialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.OptionDialogBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;

import java.util.HashMap;
import java.util.List;

public class OptionDialog extends DialogFragment {
    DialogsManager dialogsManager;
    int quantity = 1;
    public static OptionDialog newInstance(DialogsManager dialogsManager, String name, String money, String urlImage, HashMap<String, List<String>> options) {
        Bundle args = new Bundle();
        OptionDialog fragment = new OptionDialog();
        fragment.setArguments(args);
        fragment.dialogsManager = dialogsManager;
        return fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        OptionDialogBinding binding = OptionDialogBinding.inflate(getLayoutInflater());
        binding.button.setOnClickListener(v -> {
            OptionEvent event = new OptionEvent(null, 1);
            dialogsManager.postEvent(event);
            dismiss();
        });
        binding.minus.setOnClickListener(v -> {
            if (quantity > 1) {
                --quantity;
                binding.quanitynum.setText(String.valueOf(quantity));
                if (quantity == 1) {
                    binding.minus.setImageResource(R.drawable.icon_remove_inactive);
                }
            }
        });
        binding.plus.setOnClickListener(v -> {
            ++quantity;
            binding.quanitynum.setText(String.valueOf(quantity));
            if (quantity > 1) {
                binding.minus.setImageResource(R.drawable.icon_remove_active);
            }
        });
        binding.close.setOnClickListener(v -> dismiss());
        Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 420, getContext().getResources().getDisplayMetrics()));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.AnimationBottomSheetDialog;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        return dialog;
    }
}
