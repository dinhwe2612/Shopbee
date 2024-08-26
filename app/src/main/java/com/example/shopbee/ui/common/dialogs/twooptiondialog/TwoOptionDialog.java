package com.example.shopbee.ui.common.dialogs.twooptiondialog;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.databinding.TwoOptionDialogBinding;
import com.example.shopbee.ui.common.dialogs.DialogsEventBus;

import javax.inject.Inject;

public class TwoOptionDialog extends DialogFragment {
    public static final String ARG_TITLE = "title";
    public static final String ARG_MESSAGE = "message";
    public static final String ARG_BUTTON_TEXT1 = "button_text_1";
    public static final String ARG_BUTTON_TEXT2 = "button_text_2";

    public static TwoOptionDialog newInstance(String title, String message, String buttonText1, String buttonText2) {
        TwoOptionDialog dialog = new TwoOptionDialog();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);
        args.putString(ARG_BUTTON_TEXT1, buttonText1);
        args.putString(ARG_BUTTON_TEXT2, buttonText2);
        dialog.setArguments(args);
        return dialog;
    }
    @Inject
    DialogsEventBus dialogsEventBus;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        String title = getArguments().getString(ARG_TITLE);
        String message = getArguments().getString(ARG_MESSAGE);
        String buttonText1 = getArguments().getString(ARG_BUTTON_TEXT1);
        String buttonText2 = getArguments().getString(ARG_BUTTON_TEXT2);
        TwoOptionDialogBinding binding = TwoOptionDialogBinding.inflate(getLayoutInflater());
        binding.titleText.setText(title);
        binding.messageText.setText(message);
        binding.dialogBtn1.setText(buttonText1);
        binding.dialogBtn2.setText(buttonText2);
        binding.dialogBtn1.setOnClickListener(v -> dismiss());
        binding.dialogBtn2.setOnClickListener(v -> dismiss());
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(binding.getRoot());
        return dialog;
    }
}
