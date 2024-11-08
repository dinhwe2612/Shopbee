package com.example.shopbee.di.component;

import com.example.shopbee.BaseApplication;
import com.example.shopbee.data.Repository;
import com.example.shopbee.di.module.AppModule;
import com.example.shopbee.ui.common.bottombar.BottomBarUserReactionImplementation;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(BaseApplication application);
    Repository getRepository();
    BottomBarUserReactionImplementation getBottomBarUserReactionImplementation();
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(BaseApplication application);
        AppComponent build();
    }
}
