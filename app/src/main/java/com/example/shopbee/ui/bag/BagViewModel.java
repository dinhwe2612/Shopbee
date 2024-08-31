package com.example.shopbee.ui.bag;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

public class BagViewModel extends BaseViewModel<BagNavigator> {
    MutableLiveData<List<PromoCodeResponse>> promoCodes = new MutableLiveData<>();

    public MutableLiveData<List<PromoCodeResponse>> getPromoCodes() {
        return promoCodes;
    }

    public BagViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> bagProducts = new MutableLiveData<>();
    MutableLiveData<List<String>> bagLists = new MutableLiveData<>();
    MutableLiveData<List<List<Pair<String, String>>>> bagVariations = new MutableLiveData<>();

    public MutableLiveData<List<List<Pair<String, String>>>> getBagVariations() {
        return bagVariations;
    }

    public MutableLiveData<List<String>> getBagLists() {
        return bagLists;
    }

    public MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> getBagProducts() {
        return bagProducts;
    }

    public void getBagVariationFromFirebase() {

    }

    public void syncBagLists() {
        // get users' favorite asins and variations
//        favoriteLists.observe(getLifeCycleOwner.getLifeCycleOwner(), lists -> {
//            syncFavoriteProducts(favoriteLists.getValue());
//        });
        syncBagProducts(bagLists.getValue());
    }
    public void syncBagProducts(List<String> bagLists) {
        // get information from Amazon asin
    }
    public void syncPromoCodes() {

    }
}
