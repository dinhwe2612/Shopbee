package com.example.shopbee.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.example.shopbee.data.Repository;
import com.example.shopbee.di.ViewModelProviderFactory;
import com.example.shopbee.ui.base.BaseActivity;
import com.example.shopbee.ui.login.LoginViewModel;
import com.example.shopbee.ui.main.MainViewModel;

import java.util.function.Supplier;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    BaseActivity<?, ?> activity;
    public ActivityModule(BaseActivity<?, ?> activity) {
        this.activity = activity;
    }
    @Provides
    LoginViewModel provideLoginViewModel(Repository repository) {
        Supplier<LoginViewModel> supplier = () -> new LoginViewModel(repository);
        ViewModelProviderFactory<LoginViewModel> factory = new ViewModelProviderFactory<>(LoginViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(LoginViewModel.class);
    }
    @Provides
    MainViewModel provideMainViewModel(Repository repository) {
        Supplier<MainViewModel> supplier = () -> new MainViewModel(repository);
        ViewModelProviderFactory<MainViewModel> factory = new ViewModelProviderFactory<MainViewModel>(MainViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }
}
