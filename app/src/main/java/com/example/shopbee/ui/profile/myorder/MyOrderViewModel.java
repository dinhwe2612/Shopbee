package com.example.shopbee.ui.profile.myorder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

public class MyOrderViewModel extends BaseViewModel {
    public MyOrderViewModel(Repository repository) {
        super(repository);
    }
}