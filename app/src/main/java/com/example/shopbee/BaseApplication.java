package com.example.shopbee;

import android.app.Application;

import com.example.shopbee.di.component.AppComponent;
import com.example.shopbee.di.component.DaggerAppComponent;
import com.example.shopbee.di.module.AppModule;
import com.google.firebase.FirebaseApp;

public class BaseApplication extends Application {
    public AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .build();
        appComponent.inject(this);
        FirebaseApp.initializeApp(this);
    }
}
