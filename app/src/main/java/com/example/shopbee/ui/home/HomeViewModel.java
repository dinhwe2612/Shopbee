package com.example.shopbee.ui.home;

import android.util.Log;

import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.ui.base.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    public HomeViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<List<AmazonDealsResponse.Data.Deal>> dealProducts = new MutableLiveData<>();
    public void syncDealProducts(HashMap<String, String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAmazonDealsResponse(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            setIsLoading(false);
                            dealProducts.setValue(result.getData().getDeals());
                        },
                        error -> {
                            setIsLoading(false);
                            Log.e("getAmazonDealsResponse", "getAmazonDealsResponse " + error.getMessage());
                        })
                );
    }
    public MutableLiveData<List<AmazonDealsResponse.Data.Deal>> getDealProducts() {
        return dealProducts;
    }
}
