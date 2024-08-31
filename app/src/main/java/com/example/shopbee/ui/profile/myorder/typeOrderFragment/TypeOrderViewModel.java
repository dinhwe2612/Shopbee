package com.example.shopbee.ui.profile.myorder.typeOrderFragment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

public class TypeOrderViewModel extends BaseViewModel {
    private final MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    private final MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();
    public TypeOrderViewModel(Repository repository) {

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
    public MutableLiveData<UserResponse> getUserResponse() {
        return userResponse;
    }
    public MutableLiveData<ListOrderResponse> getOrderResponse() {
        return listOrderResponse;
    }
}
