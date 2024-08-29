package com.example.shopbee.di.component;


import com.example.shopbee.di.module.FragmentModule;
import com.example.shopbee.di.scope.FragmentScope;
import com.example.shopbee.ui.bag.BagFragment;
import com.example.shopbee.ui.favorites.FavoritesFragment;
import com.example.shopbee.ui.home.HomeFragment;
import com.example.shopbee.ui.productdetail.ProductDetailFragment;
import com.example.shopbee.ui.profile.ProfileFragment;
import com.example.shopbee.ui.profile.myorder.MyOrderDetailFragment;
import com.example.shopbee.ui.profile.myorder.MyOrderFragment;
import com.example.shopbee.ui.profile.setting.SettingsFragment;
import com.example.shopbee.ui.shop.ShopFragment;
import com.example.shopbee.ui.shop.search.SearchFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = {AppComponent.class, ActivityComponent.class})
public interface FragmentComponent {
    void inject(SearchFragment searchFragment);
    void inject(ProfileFragment profileFragment);
    void inject(FavoritesFragment favoritesFragment);
    void inject(BagFragment bagFragment);
    void inject(HomeFragment homeFragment);
    void inject(ShopFragment shopFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(MyOrderFragment myOrderFragment);
    void inject(MyOrderDetailFragment myOrderDetailFragment);
    void inject(ProductDetailFragment ProductDetailFragment);
}
