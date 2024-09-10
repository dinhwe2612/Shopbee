package com.example.shopbee.ui.profile;

public interface ProfileNavigator {
    void handleError(String message);
    void myOrder();
    void shippingAddresses();
    void paymentMethods();
    void promocodes();
    void myReviews();
    void setting();
    void openMainActivity();
    void navigateToLogin();
}
