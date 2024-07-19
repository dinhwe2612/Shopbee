package com.example.shopbee.bottombar;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopbee.R;

public class BottomBarUserReactionImplementation implements BottomBarUserReactionListener {
    int position = 0;
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
    public void onClick(ImageView[] icons, TextView[] labels, int position) {
        UIUnselected(icons[this.position], labels[this.position], this.position);
        UISelected(icons[position], labels[position], position);
        this.position = position;
    }
}
