package com.example.shopbee.ui.favorites;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

public class FavoritesViewModel extends BaseViewModel<FavoritesNavigator> {
    MutableLiveData<List<AmazonProductByCategoryResponse.Data.Product>> favoriteProducts = new MutableLiveData<>();
    MutableLiveData<List<String>> favoriteLists = new MutableLiveData<>();
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
        favoriteLists.observe((LifecycleOwner) this, lists -> {
            syncFavoriteProducts(favoriteLists.getValue());
        });
    }
    public void syncFavoriteProducts(List<String> favoriteLists) {
        // get information from Amazon asin
    }
}
