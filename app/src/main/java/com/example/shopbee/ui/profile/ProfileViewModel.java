package com.example.shopbee.ui.profile;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {
    private final MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    private final MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> avatar = new MutableLiveData<>();
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
    public void syncImageBitmapFirebase(String imageName, String userEmail){
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getImageBitmapFirebase(imageName, userEmail)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            setIsLoading(false);
                            avatar.setValue(result);
                            Log.d("TAG1", "syncImageBitmapFirebase: " + avatar.toString());
                        },
                        error -> {
                            setIsLoading(false);
                            Log.e("getImageBitmapFirebase", "getImageBitmapFirebase " + error.getMessage());
                        })
        );
    }
    public void uploadImageBitmapFirebase(Bitmap bitmap, String imageName, String userEmail){
        getCompositeDisposable().add(getRepository().uploadImageBitmapFirebase(bitmap, imageName, userEmail)
                .subscribeOn(Schedulers.io())
                .subscribe()
        );
    }
    public MutableLiveData<Bitmap> getAvatar() {
        return avatar;
    }
}
