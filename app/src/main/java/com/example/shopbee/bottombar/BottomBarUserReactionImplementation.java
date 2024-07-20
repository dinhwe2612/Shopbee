package com.example.shopbee.bottombar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbee.R;

public class BottomBarUserReactionImplementation implements BottomBarUserReactionListener {
    int position = 0;

    public static float dpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public void animateBackground(View view, int position) {
        float translationX = dpToPx(view.getContext(),2 + 70*position);
        if (position == 3) {
            translationX += 15;
        }
        if (position == 4) {
            translationX += 31;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", translationX);
        animator.setDuration(300);
        animator.start();
    }
    @Override
    public void UIUnselected(ImageView icon, TextView label, int position) {
        switch (position) {
            case 0:
                icon.setImageResource(R.drawable.home_icon);
                label.setTextAppearance(R.style.Gray_Regular_10dp);
                break;
            case 1:
                icon.setImageResource(R.drawable.shop_icon);
                label.setTextAppearance(R.style.Gray_Regular_10dp);
                break;
            case 2:
                icon.setImageResource(R.drawable.bag_icon);
                label.setTextAppearance(R.style.Gray_Regular_10dp);
                break;
            case 3:
                icon.setImageResource(R.drawable.heart_icon);
                label.setTextAppearance(R.style.Gray_Regular_10dp);
                break;
            case 4:
                icon.setImageResource(R.drawable.profile_icon);
                label.setTextAppearance(R.style.Gray_Regular_10dp);
                break;
            default:
                break;
        }
    }

    @Override
    public void UISelected(ImageView icon, TextView label, int position) {
        switch (position) {
            case 0:
                icon.setImageResource(R.drawable.red_home_icon);
                label.setTextAppearance(R.style.Red_Regular_10dp);
                break;
            case 1:
                icon.setImageResource(R.drawable.red_shop_icon);
                label.setTextAppearance(R.style.Red_Regular_10dp);
                break;
            case 2:
                icon.setImageResource(R.drawable.red_bag_icon);
                label.setTextAppearance(R.style.Red_Regular_10dp);
                break;
            case 3:
                icon.setImageResource(R.drawable.red_heart_icon);
                label.setTextAppearance(R.style.Red_Regular_10dp);
                break;
            case 4:
                icon.setImageResource(R.drawable.red_profile_icon);
                label.setTextAppearance(R.style.Red_Regular_10dp);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(ImageView[] icons, TextView[] labels, View view, int position) {
        UIUnselected(icons[this.position], labels[this.position], this.position);
        UISelected(icons[position], labels[position], position);
        animateBackground(view, position);
        this.position = position;
    }
}
