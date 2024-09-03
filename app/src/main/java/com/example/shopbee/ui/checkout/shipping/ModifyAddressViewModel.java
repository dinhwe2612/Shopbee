package com.example.shopbee.ui.checkout.shipping;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

public class ModifyAddressViewModel extends BaseViewModel {
    MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    public ModifyAddressViewModel(Repository repository) {
        super(repository);
        repository.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
                userResponse.setValue(response);
            }
        });
    }
    public MutableLiveData<UserResponse> getUserResponse() {
        return userResponse;
    }
    public void updateUserFirebase(){
        getRepository().updateUserFirebase();
    }
}
