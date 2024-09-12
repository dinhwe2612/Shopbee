package com.example.shopbee.di.module;

import android.view.LayoutInflater;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.shopbee.data.Repository;
import com.example.shopbee.di.ViewModelProviderFactory;
import com.example.shopbee.ui.common.base.BaseActivity;
import com.example.shopbee.ui.common.dialogs.DialogsManager;
import com.example.shopbee.ui.flappybee.GameViewModel;
import com.example.shopbee.ui.forgotpassword.ForgotPasswordViewModel;
import com.example.shopbee.ui.login.LoginViewModel;
import com.example.shopbee.ui.main.MainViewModel;
import com.example.shopbee.ui.signup.SignUpActivity;
import com.example.shopbee.ui.signup.SignUpViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.function.Supplier;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    BaseActivity<?, ?> activity;
    Toolbar toolbar;
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

    @Provides
    GameViewModel provideGameViewModel(Repository repository) {
        Supplier<GameViewModel> supplier = () -> new GameViewModel(repository);
        ViewModelProviderFactory<GameViewModel> factory = new ViewModelProviderFactory<>(GameViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(GameViewModel.class);
    }

    @Provides
    ForgotPasswordViewModel provideForgotPasswordViewModel(Repository repository) {
        Supplier<ForgotPasswordViewModel> supplier = () -> new ForgotPasswordViewModel(repository);
        ViewModelProviderFactory<ForgotPasswordViewModel> factory = new ViewModelProviderFactory<>(ForgotPasswordViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(ForgotPasswordViewModel.class);
    }

    @Provides
    SignUpViewModel provideSignUpViewModel(Repository repository) {
        Supplier<SignUpViewModel> supplier = () -> new SignUpViewModel(repository);
        ViewModelProviderFactory<SignUpViewModel> factory = new ViewModelProviderFactory<>(SignUpViewModel.class, supplier);
        return new ViewModelProvider(activity, factory).get(SignUpViewModel.class);
    }

    @Provides
    LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(activity);
    }

    @Provides
    DialogsManager provideDialogsManager(LayoutInflater layoutInflater) {
        return new DialogsManager(activity, activity.getSupportFragmentManager());
    }
}
