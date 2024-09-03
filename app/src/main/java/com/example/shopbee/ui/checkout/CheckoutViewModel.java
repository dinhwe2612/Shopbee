package com.example.shopbee.ui.checkout;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

public class CheckoutViewModel extends BaseViewModel {
    MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();
    public CheckoutViewModel(Repository repository) {
        super(repository);
        repository.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
                userResponse.setValue(response);
            }
        });
        repository.getOrderResponse().observeForever(new Observer<ListOrderResponse>() {
            @Override
            public void onChanged(ListOrderResponse responses) {
                listOrderResponse.setValue(responses);
            }
        });
    }
    public MutableLiveData<ListOrderResponse> getOrderResponse() {
        return listOrderResponse;
    }
    public MutableLiveData<UserResponse> getUserResponse() {
        return userResponse;
    }
    public void updateUserFirebase() {
        getRepository().updateUserFirebase();
    }
    public void updateOrderFirebase(OrderResponse orderResponse) {
        getRepository().updateOrderFirebase(orderResponse);
    }
}
