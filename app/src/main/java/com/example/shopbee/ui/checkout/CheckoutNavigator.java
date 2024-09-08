package com.example.shopbee.ui.checkout;

public interface CheckoutNavigator {
    void handleError(String message);
    void changeShippingAddress();
    void changePaymentMethod();
    void backToPreviousFragment();
    void navigateToSuccessFragment();
}
