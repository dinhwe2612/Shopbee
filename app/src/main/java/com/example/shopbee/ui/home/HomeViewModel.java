package com.example.shopbee.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonBestSellerResponse;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel<HomeNavigator> {
    public HomeViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<List<AmazonDealsResponse.Data.Deal>> dealProducts = new MutableLiveData<>();
    MutableLiveData<List<AmazonBestSellerResponse.Data.BestSeller>> bestSellerProducts = new MutableLiveData<>();
    MutableLiveData<List<AmazonDealsResponse.Data.Deal>> recommendedProducts = new MutableLiveData<>();

    public void syncDealProducts(HashMap<String, String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAmazonDealsResponse(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            setIsLoading(false);
                            dealProducts.setValue(result.getData().getDeals());
                            recommendedProducts.setValue(result.getData().getDeals());
                        },
                        error -> {
                            setIsLoading(false);
                            Log.e("getAmazonDealsResponse", "getAmazonDealsResponse " + error.getMessage());
                        })
                );
    }
    public void syncBestSellerProducts(HashMap<String, String> amazonDealsQuery) {
        getCompositeDisposable().add(getRepository().getAmazonBestSeller(amazonDealsQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    bestSellerProducts.setValue(result.getData().getBest_sellers());
                },
                error -> {
                    Log.e("getAmazonBestSeller", "getAmazonBestSeller " + error.getMessage());
                })
        );
    }
    public void syncRecommendedProducts(HashMap<String, String> amazonDealsQuery) {

    }
    public MutableLiveData<List<AmazonDealsResponse.Data.Deal>> getDealProducts() {
        return dealProducts;
    }
    public MutableLiveData<List<AmazonBestSellerResponse.Data.BestSeller>> getBestSellerProducts() {
        return bestSellerProducts;
    }

    public LiveData<List<AmazonDealsResponse.Data.Deal>> getRecommendedProducts() {
        return recommendedProducts;
    }
}
