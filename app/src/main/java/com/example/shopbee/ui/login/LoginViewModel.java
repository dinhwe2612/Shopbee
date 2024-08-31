package com.example.shopbee.ui.login;

import android.util.Log;

import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.Async;

import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public LoginViewModel(Repository repository) {
        super(repository);
    }
    public void login(String email, String password) {
        Log.d("LoginViewModel", "login called with email: " + email + ", password: " + password);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                getNavigator().openMainActivity();
            } else {
                getNavigator().handleError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public void loginWithGoogle(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getNavigator().openMainActivity();
                    } else {
                        getNavigator().handleError(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }
}
