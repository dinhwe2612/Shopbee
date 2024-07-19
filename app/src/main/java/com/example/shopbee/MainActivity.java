package com.example.shopbee;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopbee.custom.CustomFontTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.test_button);
        CustomFontTextView customFontTextView = findViewById(R.id.customFontTextView);

        String[] texts = {
                "SUMMER SALES\n",
                "\n",
                "Up to 50% off\n",
        };

        int[] fontResIds = {
                R.font.metropolis_semi_bold,
                R.font.metropolis_medium,
                R.font.metropolis_medium,
        };

        int[] sizes = {
                24, // 20sp for line 1
                10,
                14, // 24sp for line 2
        };

        customFontTextView.setCustomText(texts, fontResIds, sizes);
    }
}