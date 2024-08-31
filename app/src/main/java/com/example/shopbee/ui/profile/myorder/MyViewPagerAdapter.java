package com.example.shopbee.ui.profile.myorder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopbee.data.model.OrderProductItem;
import com.example.shopbee.ui.profile.myorder.typeOrderFragment.TypeOrderFragment;

import java.util.List;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new TypeOrderFragment("delivered");
            case 1:
                return new TypeOrderFragment("processing");
            case 2:
                return new TypeOrderFragment("cancelled");
            default:
                return new TypeOrderFragment("delivered");
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
