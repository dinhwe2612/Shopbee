package com.example.shopbee.ui.favorites;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.UserVariationResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoritesViewModel extends BaseViewModel<FavoritesNavigator> {
    public interface GetLifeCycleOwner {
        LifecycleOwner getLifeCycleOwner();
    }
    GetLifeCycleOwner getLifeCycleOwner;

    public void setGetLifeCycleOwner(GetLifeCycleOwner getLifeCycleOwner) {
        this.getLifeCycleOwner = getLifeCycleOwner;
    }

    MutableLiveData<List<AmazonProductDetailsResponse>> favoriteProducts = new MutableLiveData<>();
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

    public MutableLiveData<List<AmazonProductDetailsResponse>> getFavoriteProducts() {
        return favoriteProducts;
    }

    public void getFavoriteVariationFromFirebase() {

    }

    public void syncFavoriteLists() {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getUserVariation(Repository.UserVariation.FAVORITE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
//                            setIsLoading(false);
                            List<String> asins = new ArrayList<>();
                            List<List<Pair<String, String>>> variations = new ArrayList<>();
                            for (UserVariationResponse.Variation variation : result.getVariations()) {
                                asins.add(variation.getAsin());
                                variations.add(variation.getVariation());
                            }
                            favoriteLists.setValue(asins);
                            favoriteVariations.setValue(variations);
                            syncFavoriteProducts(favoriteLists.getValue());
                        },
                        error -> {
//                            setIsLoading(false);
//                            Log.e("getUserSearchHistory", "getUserSearchHistory " + error.getMessage());
                        })
        );
    }
    public void syncFavoriteListsOnly() {
        getCompositeDisposable().add(getRepository().getUserVariation(Repository.UserVariation.FAVORITE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
//                            setIsLoading(false);
                                    List<String> asins = new ArrayList<>();
                                    List<List<Pair<String, String>>> variations = new ArrayList<>();
                                    for (UserVariationResponse.Variation variation : result.getVariations()) {
                                        asins.add(variation.getAsin());
                                        variations.add(variation.getVariation());
                                    }
                                    favoriteLists.setValue(asins);
                                    favoriteVariations.setValue(variations);
                                },
                                error -> {
//                            setIsLoading(false);
//                            Log.e("getUserSearchHistory", "getUserSearchHistory " + error.getMessage());
                                })
        );
    }
    public void syncFavoriteProducts(List<String> favoriteLists) {
        setIsLoading(true);
        // get information from Amazon asin
        List<AmazonProductDetailsResponse> products = new ArrayList<>();
        if (products.size() == favoriteLists.size()) {
            setIsLoading(false);
            return;
        }
        List<Observable<AmazonProductDetailsResponse>> observables = new ArrayList<>();

        for (String asin : favoriteLists) {
            HashMap<String, String> queryMap = new HashMap<>();
            queryMap.put("asin", asin);

            Observable<AmazonProductDetailsResponse> observable = getRepository().getAmazonProductDetails(queryMap)
                    .doOnNext(result -> {
                        products.add(result); // Add result to products list
//                        index.getAndIncrement(); // Increment the index
                    })
                    .doOnError(error -> Log.e("syncFavoriteProducts", "error: " + error.getMessage()));

            observables.add(observable);
        }

// Use concatMap to ensure serial execution of the observables
        getCompositeDisposable().add(
                Observable.concat(observables)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    // You could handle each result here if needed
                                },
                                error -> {
                                    Log.e("syncFavoriteProducts", "Final error: " + error.getMessage());
                                },
                                () -> {
                                    // This is called once all observables have completed
                                    favoriteProducts.setValue(products);
                                    setIsLoading(false);
                                }
                        )
        );
    }
}
