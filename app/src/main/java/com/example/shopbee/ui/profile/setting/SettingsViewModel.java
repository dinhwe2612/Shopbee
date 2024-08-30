package com.example.shopbee.ui.profile.setting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

public class SettingsViewModel extends BaseViewModel {
    MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();
    Repository repository;
    public SettingsViewModel(Repository repository) {
        super(repository);
        this.repository = repository;
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
    public void updateUserFirebase(){
        repository.updateUserFirebase();
    }
}
