package com.example.shopbee.di.component;


import com.example.shopbee.di.module.FragmentModule;
import com.example.shopbee.di.scope.FragmentScope;
import com.example.shopbee.ui.bag.BagFragment;
import com.example.shopbee.ui.favorites.FavoritesFragment;
import com.example.shopbee.ui.home.HomeFragment;
import com.example.shopbee.ui.profile.ProfileFragment;
import com.example.shopbee.ui.profile.setting.SettingsFragment;
import com.example.shopbee.ui.shop.ShopFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = {AppComponent.class, ActivityComponent.class})
public interface FragmentComponent {
    void inject(ProfileFragment profileFragment);
    void inject(FavoritesFragment favoritesFragment);
    void inject(BagFragment bagFragment);
    void inject(HomeFragment homeFragment);
    void inject(ShopFragment shopFragment);
    void inject(SettingsFragment settingsFragment);
}
