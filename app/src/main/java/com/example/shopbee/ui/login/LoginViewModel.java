package com.example.shopbee.ui.login;

import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.base.BaseViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;

import java.util.Objects;


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
