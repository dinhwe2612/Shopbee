package com.example.shopbee.datas.remote;

import com.example.shopbee.datas.model.api.AmazonSearchResponse;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface AmazonApiService {
    @GET("search")
    Observable<AmazonSearchResponse> search(@QueryMap Map<String, String> queries);
}
