package com.example.shopbee.di.module;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbee.data.Repository;
import com.example.shopbee.di.ViewModelProviderFactory;
import com.example.shopbee.ui.bag.BagViewModel;
import com.example.shopbee.ui.favorites.FavoritesViewModel;
import com.example.shopbee.ui.home.HomeViewModel;
import com.example.shopbee.ui.main.MainViewModel;
import com.example.shopbee.ui.productdetail.ProductDetailViewModel;
import com.example.shopbee.ui.profile.ProfileViewModel;
import com.example.shopbee.ui.profile.myorder.MyOrderDetailViewModel;
import com.example.shopbee.ui.profile.myorder.MyOrderViewModel;
import com.example.shopbee.ui.profile.setting.SettingsViewModel;
import com.example.shopbee.ui.search.SearchViewModel;
import com.example.shopbee.ui.shop.ShopViewModel;
import com.example.shopbee.ui.user_search.UserSearchViewModel;

import java.util.function.Supplier;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    Fragment fragment;
    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    public HomeViewModel provideHomeViewModel(Repository repository) {
        Supplier<HomeViewModel> supplier = () -> new HomeViewModel(repository);
        ViewModelProviderFactory<HomeViewModel> factory = new ViewModelProviderFactory<HomeViewModel>(HomeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(HomeViewModel.class);
    }

    @Provides
    public ShopViewModel provideShopViewModel(Repository repository) {
        Supplier<ShopViewModel> supplier = () -> new ShopViewModel(repository);
        ViewModelProviderFactory<ShopViewModel> factory = new ViewModelProviderFactory<ShopViewModel>(ShopViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ShopViewModel.class);
    }

    @Provides
    public BagViewModel provideBagViewModel(Repository repository) {
        Supplier<BagViewModel> supplier = () -> new BagViewModel(repository);
        ViewModelProviderFactory<BagViewModel> factory = new ViewModelProviderFactory<BagViewModel>(BagViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(BagViewModel.class);
    }

    @Provides
    public FavoritesViewModel provideFavoritesViewModel(Repository repository) {
        Supplier<FavoritesViewModel> supplier = () -> new FavoritesViewModel(repository);
        ViewModelProviderFactory<FavoritesViewModel> factory = new ViewModelProviderFactory<FavoritesViewModel>(FavoritesViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(FavoritesViewModel.class);
    }

    @Provides
    public ProfileViewModel provideProfileViewModel(Repository repository) {
        Supplier<ProfileViewModel> supplier = () -> new ProfileViewModel(repository);
        ViewModelProviderFactory<ProfileViewModel> factory = new ViewModelProviderFactory<ProfileViewModel>(ProfileViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ProfileViewModel.class);
    }
    @Provides
    public SettingsViewModel provideSettingsViewModel(Repository repository) {
        Supplier<SettingsViewModel> supplier = () -> new SettingsViewModel(repository);
        ViewModelProviderFactory<SettingsViewModel> factory = new ViewModelProviderFactory<SettingsViewModel>(SettingsViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(SettingsViewModel.class);
    }
    @Provides
    public MyOrderViewModel provideMyOrderViewModel(Repository repository) {
        Supplier<MyOrderViewModel> supplier = () -> new MyOrderViewModel(repository);
        ViewModelProviderFactory<MyOrderViewModel> factory = new ViewModelProviderFactory<MyOrderViewModel>(MyOrderViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MyOrderViewModel.class);
    }
    @Provides
    public SearchViewModel provideSearchViewModel(Repository repository) {
        Supplier<SearchViewModel> supplier = () -> new SearchViewModel(repository);
        ViewModelProviderFactory<SearchViewModel> factory = new ViewModelProviderFactory<SearchViewModel>(SearchViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(SearchViewModel.class);
    }
    @Provides
    public MyOrderDetailViewModel provideMyOrderDetailViewModel(Repository repository) {
        Supplier<MyOrderDetailViewModel> supplier = () -> new MyOrderDetailViewModel(repository);
        ViewModelProviderFactory<MyOrderDetailViewModel> factory = new ViewModelProviderFactory<MyOrderDetailViewModel>(MyOrderDetailViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(MyOrderDetailViewModel.class);
    }
    @Provides
    public ProductDetailViewModel provideProductDetailViewModel(Repository repository) {
        Supplier<ProductDetailViewModel> supplier = () -> new ProductDetailViewModel(repository);
        ViewModelProviderFactory<ProductDetailViewModel> factory = new ViewModelProviderFactory<ProductDetailViewModel>(ProductDetailViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(ProductDetailViewModel.class);
    }
    @Provides
    public UserSearchViewModel provideUserSearchViewModel(Repository repository) {
        Supplier<UserSearchViewModel> supplier = () -> new UserSearchViewModel(repository);
        ViewModelProviderFactory<UserSearchViewModel> factory = new ViewModelProviderFactory<UserSearchViewModel>(UserSearchViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(UserSearchViewModel.class);
    }
}
