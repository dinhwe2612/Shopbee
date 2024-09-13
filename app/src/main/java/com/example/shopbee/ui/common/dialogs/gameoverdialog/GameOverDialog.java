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

import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.databinding.GameoverDialogBinding;
import com.example.shopbee.ui.common.dialogs.DialogsManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
        binding.scoreMessage.setText("Your score is " + score + ".");
        if (score < 5){ //no voucher
            binding.resultText.setText("You don't receive a voucher. Try again!");
        }
        else if (score >= 5 && score < 10){ //save flappy voucher 30%
            binding.resultText.setText("Congratulation! You receive a voucher at 30%. Voucher is sent to your voucher list.");
        } else if (score >= 10 && score < 15){ //save flappy voucher 60%
            binding.resultText.setText("Congratulation! You receive a voucher at 60%. Voucher is sent to your voucher list.");
        } else if (score >= 15){ //save flappy voucher 90%
            binding.resultText.setText("Congratulation! You receive a voucher at 90%. Voucher is sent to your voucher list.");
        }
        binding.home.setOnClickListener(v -> {
            dialogsManager.postEvent(new GameOverEvent(GameOverEvent.GameOver.HOME));
            dismiss();
        });
        binding.playagain.setOnClickListener(v -> {
            dialogsManager.postEvent(new GameOverEvent(GameOverEvent.GameOver.PLAYAGAIN));
            dismiss();
        });
        binding.scoreMessage.setText("You scored " + score + " points.");
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
