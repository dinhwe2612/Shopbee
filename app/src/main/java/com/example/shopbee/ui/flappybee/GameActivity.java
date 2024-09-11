package com.example.shopbee.ui.flappybee;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbee.R;
import com.example.shopbee.databinding.GameBinding;

public class GameActivity extends AppCompatActivity implements GameView.Listener {
    GameBinding binding;
    Integer score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = GameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize the private views
        binding.gameView.setListener(this);
        binding.play.setOnClickListener(v->{
            binding.startingGame.setVisibility(View.GONE);
            binding.gameView.setVisibility(View.VISIBLE);
            binding.gameView.resume();
        });
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
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Game Over")
                    .setMessage("Your score is " + score.toString())
                    .setPositiveButton("Restart", (dialog1, which) -> {
                        score = 0;
                        binding.gameView.resume();
                    })
                    .setNegativeButton("Exit", (dialog1, which) -> {
                        binding.startingGame.setVisibility(View.VISIBLE);
                        binding.gameView.setVisibility(View.GONE);
                        score = 0;
                    })
                    .create();
            dialog.show();
        });
    }
}
