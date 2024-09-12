package com.example.shopbee.ui.main;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel<MainNavigator> {
    public MainViewModel(Repository repository) {
        super(repository);
    }
    public void syncDataResponse(String email){
        setIsLoading(true);
        Observable<UserResponse> userInfoObservable = getRepository().getUserInformation(email)
                .subscribeOn(Schedulers.io());

        Observable<ListOrderResponse> orderInfoObservable = getRepository().getListOrderInformation(email)
                .subscribeOn(Schedulers.io());

        getCompositeDisposable().add(Observable.zip(userInfoObservable, orderInfoObservable,
                        (userResponse, listOrderResponse) -> new Pair(userResponse, listOrderResponse))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setIsLoading(false);
                }));
    }
}
