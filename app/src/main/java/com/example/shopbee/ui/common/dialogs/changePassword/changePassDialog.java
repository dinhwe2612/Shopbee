package com.example.shopbee.ui.common.dialogs.changePassword;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ChangePasswordBinding;
import com.example.shopbee.ui.common.dialogs.DialogsEventBus;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import javax.inject.Inject;

public class changePassDialog extends DialogFragment {
    public static final String ARG_OLD_PASSWORD = "old_password";
    public static final String ARG_FULL_NAME = "full_name";
    public static final String ARG_EMAIL = "email";
    ChangePasswordBinding binding;

    public static changePassDialog newInstance(String old_password, String full_name, String email) {
        changePassDialog dialog = new changePassDialog();
        Bundle args = new Bundle();
        args.putString(ARG_OLD_PASSWORD, old_password);
        args.putString(ARG_FULL_NAME, full_name);
        args.putString(ARG_EMAIL, email);
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
        String old_password = getArguments().getString(ARG_OLD_PASSWORD);
        String full_name = getArguments().getString(ARG_FULL_NAME);
        String email = getArguments().getString(ARG_EMAIL);
        binding = ChangePasswordBinding.inflate(getLayoutInflater());

        binding.oldPassword.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().isEmpty()){
                    binding.OldPasswordHint.setVisibility(View.GONE);
                } else {
                    binding.OldPasswordHint.setVisibility(View.VISIBLE);
                }
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               binding.oldPassword.setHintTextColor(getResources().getColor(R.color.gray));
               binding.OldPasswordHint.setTextColor(getResources().getColor(R.color.gray));
               binding.oldPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle);
               if (s.toString().isEmpty()){
                   binding.OldPasswordHint.setVisibility(View.GONE);
               } else {
                   binding.OldPasswordHint.setVisibility(View.VISIBLE);
               }
           }

           @Override
           public void afterTextChanged(Editable s) {
               if (s.toString().isEmpty()){
                   binding.OldPasswordHint.setVisibility(View.GONE);
               } else {
                   binding.OldPasswordHint.setVisibility(View.VISIBLE);
               }
           }
        });
        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().isEmpty()){
                    binding.NewPasswordHint.setVisibility(View.GONE);
                } else {
                    binding.NewPasswordHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.newPassword.setHintTextColor(getResources().getColor(R.color.gray));
                binding.NewPasswordHint.setTextColor(getResources().getColor(R.color.gray));
                binding.newPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle);
                if (s.toString().isEmpty()){
                    binding.NewPasswordHint.setVisibility(View.GONE);
                } else {
                    binding.NewPasswordHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    binding.NewPasswordHint.setVisibility(View.GONE);
                } else {
                    binding.NewPasswordHint.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.repeatNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().isEmpty()){
                    binding.RepeatNewPasswordHint.setVisibility(View.GONE);
                } else {
                    binding.RepeatNewPasswordHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.repeatNewPassword.setHintTextColor(getResources().getColor(R.color.gray));
                binding.RepeatNewPasswordHint.setTextColor(getResources().getColor(R.color.gray));
                binding.repeatNewPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle);
                if (s.toString().isEmpty()){
                    binding.RepeatNewPasswordHint.setVisibility(View.GONE);
                } else {
                    binding.RepeatNewPasswordHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()){
                    binding.RepeatNewPasswordHint.setVisibility(View.GONE);
                } else {
                    binding.RepeatNewPasswordHint.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.oldPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter old password", Toast.LENGTH_SHORT).show();
                    binding.oldPassword.setHintTextColor(getResources().getColor(R.color.red));
                    binding.oldPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.newPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter new password", Toast.LENGTH_SHORT).show();
                    binding.newPassword.setHintTextColor(getResources().getColor(R.color.red));
                    binding.newPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.repeatNewPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter repeat new password", Toast.LENGTH_SHORT).show();
                    binding.repeatNewPassword.setHintTextColor(getResources().getColor(R.color.red));
                    binding.repeatNewPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else if (binding.oldPassword.getText().toString().equals(old_password)) {
                    if (binding.newPassword.getText().toString().equals(binding.repeatNewPassword.getText().toString())) {
                        Toast.makeText(getContext(), "Change password successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Repeat new password must be same with new password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show();
                    binding.OldPasswordHint.setHintTextColor(getResources().getColor(R.color.red));
                    binding.oldPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                }
            }
        });
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(binding.getRoot());
        return dialog;
    }
}
