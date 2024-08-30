package com.example.shopbee.ui.login;

import android.util.Log;

import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Repository repository;
    public LoginViewModel(Repository repository) {
        super(repository);
        this.repository = repository;
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
        repository.queryUserInformation(email);
        repository.queryListOrderInformation(email);
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
