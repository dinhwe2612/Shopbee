package com.example.shopbee.ui.profile.myorder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

public class MyOrderDetailViewModel extends BaseViewModel {
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    private MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();
    public MyOrderDetailViewModel(Repository repository) {
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
}
