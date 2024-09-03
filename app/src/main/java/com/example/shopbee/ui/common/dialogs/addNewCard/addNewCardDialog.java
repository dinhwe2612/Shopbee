package com.example.shopbee.ui.common.dialogs.addNewCard;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.animsh.animatedcheckbox.AnimatedCheckBox;
import com.example.shopbee.R;
import com.example.shopbee.databinding.AddNewCardBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.changePassword.changePassDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class addNewCardDialog extends DialogFragment {
    AddNewCardBinding binding;
    private DialogsManager dialogsManager;
    private Boolean isTicked = false;

    public static addNewCardDialog newInstance(DialogsManager dialogsManager) {
        addNewCardDialog dialog = new addNewCardDialog();
        Bundle args = new Bundle();
        dialog.dialogsManager = dialogsManager;
        dialog.setArguments(args);
        return dialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        binding = AddNewCardBinding.inflate(getLayoutInflater());
        binding.checkBoxCard.setChecked(false, true);
        handleStateOfInput(binding.name, binding.nameHint, binding.nameOnCardLayout);
        handleStateOfNumberCard(binding.cardNumber, binding.cardNumberHint, binding.cardNumberLayout, binding.typeCardIcon);
        handleStateOfInput(binding.expiryDate, binding.expiryDateHint, binding.expiryDateLayout);
        handleStateOfInput(binding.cvv, binding.cvvHint, binding.cvvLayout);

        binding.checkBoxCard.setOnCheckedChangeListener(new AnimatedCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(AnimatedCheckBox checkBox, boolean isChecked) {
                isTicked = isChecked;
            }
        });
        binding.addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.name.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter name", Toast.LENGTH_SHORT).show();
                    binding.name.setHintTextColor(getResources().getColor(R.color.red));
                    binding.nameOnCardLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.cardNumber.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter card number", Toast.LENGTH_SHORT).show();
                    binding.cardNumber.setHintTextColor(getResources().getColor(R.color.red));
                    binding.cardNumberLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.expiryDate.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter expiry date", Toast.LENGTH_SHORT).show();
                    binding.expiryDate.setHintTextColor(getResources().getColor(R.color.red));
                    binding.expiryDateLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.cvv.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter cvv", Toast.LENGTH_SHORT).show();
                    binding.cvv.setHintTextColor(getResources().getColor(R.color.red));
                    binding.cvvLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else {
                    Toast.makeText(getContext(), "Add card successfully", Toast.LENGTH_SHORT).show();
                    addNewCardEvent mAddNewCardEvent = new addNewCardEvent(binding.name.getText().toString()
                            , binding.cardNumber.getText().toString()
                            , binding.cvv.getText().toString()
                            , binding.expiryDate.getText().toString()
                            , "visa"
                            , isTicked);
                    dialogsManager.postEvent(mAddNewCardEvent);
                    dismiss();
                }
            }
        });
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(binding.getRoot());
        return dialog;
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
    private void handleStateOfNumberCard(EditText editText, TextView hint, LinearLayout layout, ImageView icon) {
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
                    icon.setVisibility(View.GONE);
                } else {
                    hint.setVisibility(View.VISIBLE);
                    if (s.toString().length() == 16) {
                        icon.setVisibility(View.VISIBLE);
                    } else {
                        icon.setVisibility(View.GONE);
                    }
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
}
