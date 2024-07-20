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
    public void animateLine(TextView tag, View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = tag.getWidth() + tag.getWidth()/2;
        view.setLayoutParams(params);
        Log.d("TAG", "animateLine: "+tag.getLeft());
        float center = tag.getLeft() + (float) tag.getWidth() / 2.0f - (float) view.getWidth() / 2;
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
