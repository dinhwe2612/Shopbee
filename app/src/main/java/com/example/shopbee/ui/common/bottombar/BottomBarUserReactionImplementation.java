package com.example.shopbee.ui.component.bottombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shopbee.R;
import com.example.shopbee.data.Repository;
import com.example.shopbee.databinding.BottomBarBinding;

public class BottomBarUserReactionImplementation {
    BottomBarBinding bottomBarBinding;
    com.example.shopbee.ui.component.bottombar.BottomBarUserReactionListener listener;
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
    public void bindView(BottomBarBinding binding, com.example.shopbee.ui.component.bottombar.BottomBarUserReactionListener listener) {
        this.bottomBarBinding = binding;
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
                UISelected(currentPosition);
                animatedBackground.setX(getTranslationX(currentPosition));
                Log.d("BottomBarUserReaction", "onGlobalLayout: " + currentPosition);
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
    public void animateAddToFavorite(ImageView productImage, RelativeLayout rootView, Repository.UserVariation userVariation) {
        ImageView copiedImage = new ImageView(productImage.getContext());
        copiedImage.setImageDrawable(productImage.getDrawable());
        rootView.addView(copiedImage);

        int[] originalPos = new int[2];
        productImage.getLocationOnScreen(originalPos);
        copiedImage.setX(originalPos[0]);
        copiedImage.setY(originalPos[1]);

        int[] targetPos = new int[2];
        ImageView button;
        if (userVariation == Repository.UserVariation.FAVORITE) {
            button = imageViews[3];
        } else {
            button = imageViews[2];
        }

        button.getLocationOnScreen(targetPos);

        Log.d("BottomBarUserReaction", "targetPos: " + targetPos[0] + ", " + targetPos[1]);

        // ObjectAnimators for translation
        ObjectAnimator translateX = ObjectAnimator.ofFloat(copiedImage, "x", targetPos[0]);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(copiedImage, "y", targetPos[1]);

        // Keyframes for faster shrinking
        Keyframe kf0 = Keyframe.ofFloat(0f, 1f); // Start at full size
        Keyframe kf1 = Keyframe.ofFloat(0.3f, 0.4f); // Shrink to 40% size faster
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f); // Shrink to 0% size at the end

        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofKeyframe("scaleX", kf0, kf1, kf2);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofKeyframe("scaleY", kf0, kf1, kf2);

        // Create ObjectAnimators for scaling with keyframes
        ObjectAnimator scaleX = ObjectAnimator.ofPropertyValuesHolder(copiedImage, scaleXHolder);
        ObjectAnimator scaleY = ObjectAnimator.ofPropertyValuesHolder(copiedImage, scaleYHolder);

        // Combine all animations into AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateX, translateY, scaleX, scaleY);
//        animatorSet.playTogether(translateX, translateY);
        animatorSet.setDuration(1000); // Total duration remains the same

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rootView.removeView(copiedImage);
            }
        });

        animatorSet.start();
    }
    public void hideBottomBar() {
        bottomBarBinding.bottomBar.setVisibility(View.GONE);
    }
}
