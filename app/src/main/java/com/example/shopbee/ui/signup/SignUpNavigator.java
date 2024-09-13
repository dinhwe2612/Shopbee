package com.example.shopbee.ui.signup;

public interface SignUpNavigator {
    void signUpWithEmailAndPassWord();
    void emptyBox();
    void passwordNotMatch();
    void signUpSuccess();
    void signUpFailed(Exception exception);
    void navigateToLogin();
    void loginWithFacebook();
    void loginWithGoogle();
}
