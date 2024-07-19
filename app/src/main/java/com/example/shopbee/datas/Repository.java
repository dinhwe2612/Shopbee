package com.example.shopbee.datas;

import com.example.shopbee.datas.model.api.AmazonSearchResponse;
import com.example.shopbee.datas.remote.AmazonApiService;

import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;

@Singleton
public class Repository {
    final AmazonApiService amazonApiService;
    @Inject
    Repository(AmazonApiService amazonApiService) {
        this.amazonApiService = amazonApiService;
    }
    // query, page, country, sort_by, product_condition
    public Observable<AmazonSearchResponse> search(HashMap<String, String> query) {
        return amazonApiService.search(query);
    }
}
