package com.example.shopbee.testUI;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shopbee.R;

public class TestButtonAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button_pattern);
        TextView myButton = this.findViewById(R.id.button);
        Animation clickAnimation = AnimationUtils.loadAnimation(this, R.anim.button_click_animation);

        myButton.setOnClickListener(v -> v.startAnimation(clickAnimation));
//        EdgeToEdge.start(this);
    }
}