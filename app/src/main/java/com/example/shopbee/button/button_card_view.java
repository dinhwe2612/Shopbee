package com.example.shopbee.button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.shopbee.R;

public class button_card_view extends CardView {
    public button_card_view(@NonNull Context context) {
        super(context);
    }

    public button_card_view(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public button_card_view(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void animateButton() {
        Animation clickAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.button_click_animation);
        startAnimation(clickAnimation);
    }
}
