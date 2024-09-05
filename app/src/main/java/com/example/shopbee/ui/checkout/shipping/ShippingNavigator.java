package com.example.shopbee.ui.checkout.shipping;

import com.example.shopbee.data.model.api.AddressResponse;

public interface ShippingNavigator {
    void addNewAddressByHand();
    void editAddress(AddressResponse addressResponse, int position);
    void backToCheckout();
    void addNewAddressByMap(String country_def);
}
