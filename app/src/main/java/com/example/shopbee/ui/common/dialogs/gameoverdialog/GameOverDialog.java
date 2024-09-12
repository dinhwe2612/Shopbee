package com.example.shopbee.ui.common.dialogs.gameoverdialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.shopbee.databinding.GameoverDialogBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;

public class GameOverDialog extends DialogFragment {
    DialogsManager dialogsManager;
    int score;
    public static GameOverDialog newInstance(DialogsManager dialogsManager, int score) {
        GameOverDialog dialog = new GameOverDialog();
        dialog.score = score;
        dialog.dialogsManager = dialogsManager;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        GameoverDialogBinding binding = GameoverDialogBinding.inflate(getLayoutInflater());
        binding.home.setOnClickListener(v -> {
            dialogsManager.postEvent(new GameOverEvent(GameOverEvent.GameOver.HOME));
            dismiss();
        });
        binding.playagain.setOnClickListener(v -> {
            dialogsManager.postEvent(new GameOverEvent(GameOverEvent.GameOver.PLAYAGAIN));
            dismiss();
        });
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
