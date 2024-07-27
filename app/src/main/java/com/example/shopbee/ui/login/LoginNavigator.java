package com.example.shopbee.ui.login;

public interface LoginNavigator {
    void handleError(String message);
    void loginWithEmailAndPassWord();
    void loginWithFacebook();
    void loginWithGoogle();
    void openMainActivity();
    void toForgotPassword();
}
