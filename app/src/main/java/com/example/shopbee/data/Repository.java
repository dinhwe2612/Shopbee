package com.example.shopbee.data;

import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.AmazonSearchResponse;
import com.example.shopbee.data.model.api.CountryRespone;
import com.example.shopbee.data.remote.AmazonApiService;
import com.example.shopbee.data.remote.CountryApiService;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

@Singleton
public class Repository {
    final AmazonApiService amazonApiService;
    /*final CountryApiService countryApiService;*/
    @Inject
    Repository(AmazonApiService amazonApiService) {
        this.amazonApiService = amazonApiService;
    }
    // query, page, country, sort_by, product_condition
    public Observable<AmazonSearchResponse> search(HashMap<String, String> query) {
        return amazonApiService.search(query);
    }

    public Observable<AmazonDealsResponse> getAmazonDealsResponse(HashMap<String, String> map) {
        return amazonApiService.getAmazonDeals(map);
    }
    public Observable<AmazonProductByCategoryResponse> getAmazonProductByCategory(HashMap<String, String> map) {
        return amazonApiService.getAmazonProductByCategory(map);
    }

    public Observable<AmazonProductByCategoryResponse> getAmazonProductBySearching(HashMap<String, String> map) {
        return amazonApiService.getAmazonProductBySearching(map);
    }
    public Observable<AmazonProductDetailsResponse> getAmazonProductDetails(HashMap<String, String> map) {
        return amazonApiService.getAmazonProductDetails(map);
    }
    public Observable<List<String>> getUserSearchHistory() {
        return null;
    }
}
