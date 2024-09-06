package com.example.shopbee.ui.checkout.shipping;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

public class ModifyByMapViewModel extends BaseViewModel {
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    public ModifyByMapViewModel(Repository repository) {

        super(repository);
        repository.getUserResponse().observeForever(userResponse::setValue);
    }
    public MutableLiveData<UserResponse> getUserResponse() {
        return userResponse;
    }
    public void updateUserFirebase(){
        getRepository().updateUserFirebase();
    }
}
