package com.example.shopbee.di.component;

import com.example.shopbee.di.module.ActivityModule;
import com.example.shopbee.di.scope.ActivityScope;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.forgotpassword.ForgotPasswordActivity;
import com.example.shopbee.ui.login.LoginActivity;
import com.example.shopbee.ui.main.MainActivity;

import dagger.Component;
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {
    DialogsManager dialogsManager();
    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(ForgotPasswordActivity forgotPasswordActivity);
}
