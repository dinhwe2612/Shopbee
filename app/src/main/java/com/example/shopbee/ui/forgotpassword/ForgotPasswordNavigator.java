package com.example.shopbee.ui.forgotpassword;

public interface ForgotPasswordNavigator {
    void sendEmail();
    void sentEmailSuccess();
    void sentEmailFailed(String message);
}
