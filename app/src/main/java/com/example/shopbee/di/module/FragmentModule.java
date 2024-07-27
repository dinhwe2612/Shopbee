package com.example.shopbee.di.module;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbee.data.Repository;
import com.example.shopbee.di.ViewModelProviderFactory;
import com.example.shopbee.ui.home.HomeViewModel;
import com.example.shopbee.ui.main.MainViewModel;

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
}
