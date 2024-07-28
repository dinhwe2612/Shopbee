package com.example.shopbee.testUI.PagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shopbee.ui.bag.BagFragment;
import com.example.shopbee.ui.favorites.FavoritesFragment;
import com.example.shopbee.ui.profile.ProfileFragment;
import com.example.shopbee.ui.shop.ShopFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new ShopFragment();
            case 1:
                return new BagFragment();
            case 2:
                return new ProfileFragment();
            default:
                return new FavoritesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
