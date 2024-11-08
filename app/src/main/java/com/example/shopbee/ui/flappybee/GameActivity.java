package com.example.shopbee.ui.flappybee;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbee.R;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.databinding.GameBinding;
import com.example.shopbee.di.component.ActivityComponent;
import com.example.shopbee.ui.common.base.BaseActivity;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.common.dialogs.gameoverdialog.GameOverEvent;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GameActivity extends BaseActivity<GameBinding, GameViewModel> implements GameView.Listener, DialogsManager.Listener {
    GameBinding binding;
    Integer score = 0;
    @Inject
    DialogsManager dialogsManager;

    @Override
    public int getBindingVariable() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.game;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = getViewDataBinding();
        setContentView(binding.getRoot());

        binding.gameView.setListener(this);
        binding.play.setOnClickListener(v->{
            binding.startingGame.setVisibility(View.GONE);
            binding.gameView.setVisibility(View.VISIBLE);
            binding.gameView.resume();
        });
        binding.rule.setOnClickListener(v->{
            dialogsManager.showRuleDialog();
        });
        binding.backButton.setOnClickListener(v->{
            finish();
        });
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }

    @Override
    public void updateScore() {
        ++score;
        runOnUiThread(()->{
            binding.score.setText("Score: " + score.toString());
        });
    }

    @Override
    public void endGame() {
        runOnUiThread(() ->{
            dialogsManager.showGameOverDialog(score);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        dialogsManager.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialogsManager.unregisterListener(this);
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof GameOverEvent) {
            GameOverEvent gameOverEvent = (GameOverEvent) event;

            viewModel.savePromoCode(score);
            score = 0;
            if (gameOverEvent.getGameOver() == GameOverEvent.GameOver.HOME) {
                binding.gameView.setVisibility(View.GONE);
                binding.startingGame.setVisibility(View.VISIBLE);
            } else {
                binding.gameView.resume();
            }
        }
    }
}
