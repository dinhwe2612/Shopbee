package com.example.shopbee.di.component;


import com.example.shopbee.di.module.FragmentModule;
import com.example.shopbee.di.scope.FragmentScope;
import com.example.shopbee.ui.bag.BagFragment;
import com.example.shopbee.ui.checkout.CheckoutFragment;
import com.example.shopbee.ui.checkout.payment.PaymentFragment;
import com.example.shopbee.ui.checkout.shipping.ModifyAddressFragment;
import com.example.shopbee.ui.checkout.shipping.ShippingFragment;
import com.example.shopbee.ui.favorites.FavoritesFragment;
import com.example.shopbee.ui.home.HomeFragment;
import com.example.shopbee.ui.productdetail.ProductDetailFragment;
import com.example.shopbee.ui.profile.ProfileFragment;
import com.example.shopbee.ui.profile.myorder.MyOrderDetailFragment;
import com.example.shopbee.ui.profile.myorder.MyOrderFragment;
import com.example.shopbee.ui.profile.myorder.typeOrderFragment.TypeOrderFragment;
import com.example.shopbee.ui.profile.setting.SettingsFragment;
import com.example.shopbee.ui.review.ReviewFragment;
import com.example.shopbee.ui.shop.ShopFragment;
import com.example.shopbee.ui.search.SearchFragment;
import com.example.shopbee.ui.user_search.UserSearchFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class, dependencies = {AppComponent.class, ActivityComponent.class})
public interface FragmentComponent {
    void inject(UserSearchFragment userSearchFragment);
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
    void inject(TypeOrderFragment typeOrderFragment);
    void inject(CheckoutFragment checkoutFragment);
    void inject(PaymentFragment paymentFragment);
    void inject(ReviewFragment reviewFragment);
    void inject(ShippingFragment shippingFragment);
    void inject(ModifyAddressFragment modifyAddressFragment);
}
