package com.example.shopbee;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

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

        SharedPreferences sharedPref = getSharedPreferences("THEME", Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPref.getBoolean("DARK_MODE", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
