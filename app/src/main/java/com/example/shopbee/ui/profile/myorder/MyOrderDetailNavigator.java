package com.example.shopbee.ui.profile.myorder;

import com.example.shopbee.data.model.api.OrderResponse;

public interface MyOrderDetailNavigator {
    void backToMyOrder();
    void reorder(OrderResponse orderResponse);
    void backToHomeFragment();
}
