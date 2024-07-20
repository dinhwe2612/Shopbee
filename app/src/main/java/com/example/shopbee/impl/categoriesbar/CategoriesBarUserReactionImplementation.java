package com.example.shopbee.impl.categoriesbar;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shopbee.R;

public class CategoriesBarUserReactionImplementation implements CategoriesBarUserReactionListener{
    public int position = 0;
    @Override
    public void animateLine(TextView tag, View view, float viewX) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
//        Log.d("tag.getWidth()", "animateLine: " + tag.getWidth());
        params.width = tag.getWidth() + 25;
        Log.d("TAG", "animateLine: " + params.width);
        view.setLayoutParams(params);
        Log.d("TAG", "animateLine: "+tag.getText());
        Log.d("TAG", "animateLine: "+viewX);
        Log.d("TAG", "animateLine: "+tag.getWidth());
        Log.d("TAG", "animateLine: "+view.getWidth());
        float center = viewX + (float) tag.getWidth() / 2.0f - params.width / 2.0f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", center);
        animator.setDuration(300);
        animator.start();
    }

    @Override
    public void UIUnselected(TextView tag) {
        tag.setTextAppearance(R.style.Black_Regular_16dp);
    }

    @Override
    public void UISelected(TextView tag) {
        tag.setTextAppearance(R.style.Black_SemiBold_16dp);
    }

}
