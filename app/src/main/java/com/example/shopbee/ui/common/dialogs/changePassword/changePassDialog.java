package com.example.shopbee.ui.common.dialogs.changePassword;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.R;
import com.example.shopbee.databinding.ChangePasswordBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.forgotpassword.ForgotPasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

public class changePassDialog extends DialogFragment {
    public static final String ARG_OLD_PASSWORD = "old_password";
    public static final String ARG_FULL_NAME = "full_name";
    public static final String ARG_EMAIL = "email";
    private ChangePasswordBinding binding;
    private DialogsManager dialogsManager;

    public static changePassDialog newInstance(DialogsManager dialogsManager, String old_password, String full_name, String email) {
        changePassDialog dialog = new changePassDialog();
        Bundle args = new Bundle();
        dialog.dialogsManager = dialogsManager;
        args.putString(ARG_OLD_PASSWORD, old_password);
        args.putString(ARG_FULL_NAME, full_name);
        args.putString(ARG_EMAIL, email);
        dialog.setArguments(args);
        return dialog;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        String old_password = getArguments().getString(ARG_OLD_PASSWORD);
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
                startActivity(ForgotPasswordActivity.newIntent(getContext()));
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
                } else if (!binding.newPassword.getText().toString().equals(binding.repeatNewPassword.getText().toString())) {
                    Toast.makeText(getContext(), "Repeat new password must be same with new password", Toast.LENGTH_SHORT).show();
                    binding.newPassword.setHintTextColor(getResources().getColor(R.color.red));
                    binding.newPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                    binding.repeatNewPassword.setHintTextColor(getResources().getColor(R.color.red));
                    binding.repeatNewPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                } else {
                    String email = getArguments().getString(ARG_EMAIL);
                    String input_password = binding.oldPassword.getText().toString();
                    String new_password = binding.newPassword.getText().toString();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, input_password);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(new_password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Change password successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Auth", "Password updated.");
                                            dismiss();
                                        } else {
                                            Log.e("Auth", "Password update failed: " + task.getException().getMessage());
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show();
                                binding.OldPasswordHint.setHintTextColor(getResources().getColor(R.color.red));
                                binding.oldPasswordLayout.setBackgroundResource(R.drawable.slight_rounded_white_rectangle_red_stroke);
                            }
                        }
                    });
                }
            }
        });
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        dialog.setContentView(binding.getRoot());
        return dialog;
    }
}
