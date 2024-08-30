package com.example.shopbee.ui.favorites;

import android.util.Pair;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

public class FavoritesViewModel extends BaseViewModel<FavoritesNavigator> {
    public interface GetLifeCycleOwner {
        LifecycleOwner getLifeCycleOwner();
    }
    GetLifeCycleOwner getLifeCycleOwner;

    public void setGetLifeCycleOwner(GetLifeCycleOwner getLifeCycleOwner) {
        this.getLifeCycleOwner = getLifeCycleOwner;
    }

    MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> favoriteProducts = new MutableLiveData<>();
    MutableLiveData<List<String>> favoriteLists = new MutableLiveData<>();
    MutableLiveData<List<List<Pair<String, String>>>> favoriteVariations = new MutableLiveData<>();

    public MutableLiveData<List<List<Pair<String, String>>>> getFavoriteVariations() {
        return favoriteVariations;
    }

    public FavoritesViewModel(Repository repository) {
        super(repository);
    }

    public MutableLiveData<List<String>> getFavoriteLists() {
        return favoriteLists;
    }

    public MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void getFavoriteVariationFromFirebase() {

    }

    public void syncFavoriteLists() {
        // get users' favorite asins
//        favoriteLists.observe(getLifeCycleOwner.getLifeCycleOwner(), lists -> {
//            syncFavoriteProducts(favoriteLists.getValue());
//        });
        syncFavoriteProducts(favoriteLists.getValue());
    }
    public void syncFavoriteProducts(List<String> favoriteLists) {
        // get information from Amazon asin
    }
}
