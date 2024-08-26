package com.example.shopbee.data.remote;

import com.example.shopbee.data.model.api.AmazonDealsResponse;
import com.example.shopbee.data.model.api.AmazonSearchResponse;
import com.example.shopbee.data.model.api.CountryRespone;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CountryApiService {
    @GET("v3.1/all")
    Observable<List<CountryRespone>> getAllCountries();
}
