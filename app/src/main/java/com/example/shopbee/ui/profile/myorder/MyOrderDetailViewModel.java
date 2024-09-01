package com.example.shopbee.ui.profile.myorder;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyOrderDetailViewModel extends BaseViewModel {
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();
    private MutableLiveData<ListOrderResponse> listOrderResponse = new MutableLiveData<>();
    private MutableLiveData<List<AmazonProductDetailsResponse>> listDetailProducts = new MutableLiveData<>();

    public MyOrderDetailViewModel(Repository repository) {
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
    public void syncOrderDetailProduct(List<OrderDetailResponse> orderDetailResponse) {
        setIsLoading(true);
        List<AmazonProductDetailsResponse> products = new ArrayList<>();
        for (OrderDetailResponse detail : orderDetailResponse) {
            String asin = detail.getProduct_id();
            HashMap<String, String> queryMap = new HashMap<>();
            queryMap.put("asin", asin);
            getCompositeDisposable().add(getRepository().getAmazonProductDetails(queryMap)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
                        products.add(result);
                        listDetailProducts.setValue(products);
                    }, error -> {
                        Log.e("syncOrderDetailProduct", "error: " + error.getMessage());
                    })
            );
        }
        setIsLoading(false);
    }
    public MutableLiveData<ListOrderResponse> getOrderResponse() {
        return listOrderResponse;
    }
    public MutableLiveData<UserResponse> getUserResponse() {
        return userResponse;
    }
    public MutableLiveData<List<AmazonProductDetailsResponse>> getListDetailProducts() {return listDetailProducts;}
}
