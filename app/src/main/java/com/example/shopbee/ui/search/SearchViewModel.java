package com.example.shopbee.ui.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchViewModel extends BaseViewModel {
    public SearchViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> categoryProducts = new MutableLiveData<>();
//    MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> searchingProducts = new MutableLiveData<>();

    public MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> getCategoryProducts() {
        return categoryProducts;
    }

    public void syncProductsBySearching(HashMap<String, String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAmazonProductBySearching(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            setIsLoading(false);
                            categoryProducts.setValue(result.getData().getProducts());
                        },
                        error -> {
                            setIsLoading(false);
                            Log.e("getAmazonProductBySearching", "getAmazonProductBySearching " + error.getMessage());
                        })
        );
    }

    public void syncProductsByCategory(HashMap<String, String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAmazonProductByCategory(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            setIsLoading(false);
                            categoryProducts.setValue(result.getData().getProducts());
                        },
                        error -> {
                            setIsLoading(false);
                            Log.e("getAmazonProductByCategory", "getAmazonProductByCategory " + error.getMessage());
                        })
        );
    }
}
