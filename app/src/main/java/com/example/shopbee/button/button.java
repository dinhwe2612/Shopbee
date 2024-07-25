package com.example.shopbee.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shopbee.R;

public class button extends androidx.appcompat.widget.AppCompatTextView {
    public button(@NonNull Context context) {
        super(context);
    }

    public button(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void animateButton() {
        Animation clickAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click_animation);
        startAnimation(clickAnimation);
    }
}
