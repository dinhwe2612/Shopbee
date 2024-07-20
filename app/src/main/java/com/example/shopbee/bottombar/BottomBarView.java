package com.example.shopbee.bottombar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.R;
import com.example.shopbee.databinding.BottomBarBinding;

public class BottomBarView {
    BottomBarNavigator navigator;
    BottomBarBinding binding;
    MutableLiveData<Integer> currentPosition = new MutableLiveData<Integer>(0);
    int[] selectedImage = new int[]{
            R.drawable.red_home_icon,
            R.drawable.red_shop_icon,
            R.drawable.red_bag_icon,
            R.drawable.red_heart_icon,
            R.drawable.red_profile_icon
    };
    int[] unselectedImage = new int[]{
            R.drawable.home_icon,
            R.drawable.shop_icon,
            R.drawable.bag_icon,
            R.drawable.heart_icon,
            R.drawable.profile_icon
    };
    int []textAppearance = new int[]{
            R.style.Gray_Regular_10dp,
            R.style.Red_Regular_10dp
    };
    public BottomBarView() {

    }
    public void navigate(int position) {
        navigator.navigate(position);
    }
    public int getTextAppearance(int position) {
        int isSelect = getCurrentPosition() == position ? 1 : 0;
        return textAppearance[isSelect];
    }
    public int getImageResource(int position) {
        boolean isSelect = getCurrentPosition() == position;
        return isSelect ? selectedImage[position] : unselectedImage[position];
    }
    public int getCurrentPosition() {
        return currentPosition.getValue();
    }
    public void init(Context context) {
        binding = BottomBarBinding.inflate(LayoutInflater.from(context));
    }
}
