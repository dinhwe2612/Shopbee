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
            case 1:
                return TypeOrderFragment.newInstance("processing");
            case 2:
                return TypeOrderFragment.newInstance("cancelled");
            default:
                return TypeOrderFragment.newInstance("delivered");
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
