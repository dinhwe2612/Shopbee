package com.example.shopbee.ui.profile.myorder;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopbee.ui.profile.myorder.typeFragment.CancelledFragment;
import com.example.shopbee.ui.profile.myorder.typeFragment.DeliveredFragment;
import com.example.shopbee.ui.profile.myorder.typeFragment.ProcessingFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DeliveredFragment();
            case 1:
                return new ProcessingFragment();
            case 2:
                return new CancelledFragment();
            default:
                return new DeliveredFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}
