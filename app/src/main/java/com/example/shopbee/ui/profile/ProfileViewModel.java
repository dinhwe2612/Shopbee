package com.example.shopbee.ui.profile;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

public class ProfileViewModel extends BaseViewModel {
    private final MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    private final MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();
    public ProfileViewModel(Repository repository) {
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
