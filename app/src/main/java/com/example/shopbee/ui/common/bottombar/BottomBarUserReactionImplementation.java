package com.example.shopbee.ui.component.bottombar;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shopbee.R;
import com.example.shopbee.databinding.BottomBarBinding;

public class BottomBarUserReactionImplementation {
    BottomBarUserReactionListener listener;
    int currentPosition = 0;
    int[] selectedImages = new int[]{
            R.drawable.red_home_icon,
            R.drawable.red_shop_icon,
            R.drawable.red_bag_icon,
            R.drawable.red_heart_icon,
            R.drawable.red_profile_icon
    };
    int[] unselectedImages = new int[]{
            R.drawable.home_icon,
            R.drawable.shop_icon,
            R.drawable.bag_icon,
            R.drawable.heart_icon,
            R.drawable.profile_icon
    };
    int[] textAppearances = new int[]{
            R.style.Gray_Regular_10dp,
            R.style.Red_Regular_10dp
    };
    ImageView[] imageViews = new ImageView[5];
    TextView[] textViews = new TextView[5];
    LinearLayout[] layout = new LinearLayout[5];
    View animatedBackground;
    public void bindView(BottomBarBinding binding, BottomBarUserReactionListener listener) {
        this.listener = listener;
        imageViews[0] = binding.homeIcon;
        imageViews[1] = binding.shopIcon;
        imageViews[2] = binding.bagIcon;
        imageViews[3] = binding.heartIcon;
        imageViews[4] = binding.profileIcon;
        textViews[0] = binding.homeLabel;
        textViews[1] = binding.shopLabel;
        textViews[2] = binding.bagLabel;
        textViews[3] = binding.heartLabel;
        textViews[4] = binding.profileLabel;
        layout[0] = binding.home;
        layout[1] = binding.shop;
        layout[2] = binding.bag;
        layout[3] = binding.heart;
        layout[4] = binding.profile;
        animatedBackground = binding.animatedBackground;
        for(int i = 0; i < 5; ++i) {
            int position = i;
            layout[i].setOnClickListener(
                    v -> onClick(position)
            );
        }
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.getRoot().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                onClick(currentPosition);
            }
        });
    }

    public float getTranslationX(int position) {
        return layout[position].getLeft() + (float) layout[position].getWidth() / 2 - (float) animatedBackground.getWidth() / 2;
    }
    public void animateBackground(int position) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(animatedBackground, "translationX", getTranslationX(position));
        animator.setDuration(300);
        animator.start();
    }
    public void UIUnselected(int position) {
        imageViews[position].setImageResource(unselectedImages[position]);
        textViews[position].setTextAppearance(textAppearances[0]);
    }

    public void UISelected(int position) {
        imageViews[position].setImageResource(selectedImages[position]);
        textViews[position].setTextAppearance(textAppearances[1]);
    }

    public void onClick(int position) {
        UIUnselected(currentPosition);
        UISelected(position);
        animateBackground(position);
        currentPosition = position;
        if (listener != null) {
            listener.onClick(position);
        }
    }
}
