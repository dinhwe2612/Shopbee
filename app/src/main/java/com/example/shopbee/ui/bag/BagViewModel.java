package com.example.shopbee.ui.bag;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.data.model.api.UserVariationResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BagViewModel extends BaseViewModel<BagNavigator> {
    MutableLiveData<List<PromoCodeResponse>> promoCodes = new MutableLiveData<>();

    public MutableLiveData<List<PromoCodeResponse>> getPromoCodes() {
        return promoCodes;
    }

    public BagViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<List<AmazonProductDetailsResponse>> bagProducts = new MutableLiveData<>();
    MutableLiveData<List<String>> bagLists = new MutableLiveData<>();
    MutableLiveData<List<Integer>> bagQuantities = new MutableLiveData<>();
    public MutableLiveData<List<Integer>> getBagQuantities() {
        return bagQuantities;
    }
    MutableLiveData<List<List<Pair<String, String>>>> bagVariations = new MutableLiveData<>();

    public MutableLiveData<List<List<Pair<String, String>>>> getBagVariations() {
        return bagVariations;
    }

    public MutableLiveData<List<String>> getBagLists() {
        return bagLists;
    }

    public MutableLiveData<List<AmazonProductDetailsResponse>> getBagProducts() {
        return bagProducts;
    }

    public void getBagVariationFromFirebase() {

    }
    public void syncBagListsOnly() {
//        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getUserVariation(Repository.UserVariation.BAG)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
//                                    setIsLoading(false);
                                    List<String> asins = new ArrayList<>();
                                    List<List<Pair<String, String>>> variations = new ArrayList<>();
                                    List<Integer> quantities = new ArrayList<>();
                                    for (UserVariationResponse.Variation variation : result.getVariations()) {
                                        asins.add(variation.getAsin());
                                        variations.add(variation.getVariation());
                                        quantities.add(variation.getQuantity());
                                    }
                                    bagLists.setValue(asins);
                                    bagVariations.setValue(variations);
                                    bagQuantities.setValue(quantities);
//                                    syncBagProducts(bagLists.getValue());
                                },
                                error -> {
//                                    setIsLoading(false);
//                            Log.e("getUserSearchHistory", "getUserSearchHistory " + error.getMessage());
                                })
        );
    }

    public void syncBagLists() {
        // get users' favorite asins and variations
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getUserVariation(Repository.UserVariation.BAG)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
//                                    setIsLoading(false);
                                    List<String> asins = new ArrayList<>();
                                    List<List<Pair<String, String>>> variations = new ArrayList<>();
                                    List<Integer> quantities = new ArrayList<>();
                                    for (UserVariationResponse.Variation variation : result.getVariations()) {
                                        asins.add(variation.getAsin());
                                        variations.add(variation.getVariation());
                                        quantities.add(variation.getQuantity());
                                    }
                                    Log.d("syncBagLists", "syncBagLists: " + asins);
                                    bagLists.setValue(asins);
                                    Log.d("syncBagLists", "syncBagLists: " + variations);
                                    Log.d("syncBagLists", "syncBagLists: " + quantities);
                                    bagVariations.setValue(variations);
                                    bagQuantities.setValue(quantities);
                                    syncBagProducts(bagLists.getValue());
                                    Log.d("syncBagLists", "syncBagLists: " + bagLists.getValue());
                                },
                                error -> {
                                    setIsLoading(false);
                                    error.printStackTrace();
                                    Log.e("syncBagLists", "syncBagLists: " + error.getMessage());
//                            Log.e("getUserSearchHistory", "getUserSearchHistory " + error.getMessage());
                                })
        );
    }
    public void syncBagProducts(List<String> bagLists) {
        setIsLoading(true);
        // get information from Amazon asin
        // reload adapter many times?
        List<AmazonProductDetailsResponse> products = new ArrayList<>();
        if (products.size() == bagLists.size()) {
            setIsLoading(false);
            return;
        }
//        AtomicInteger index = new AtomicInteger();
        List<Observable<AmazonProductDetailsResponse>> observables = new ArrayList<>();

        for (String asin : bagLists) {
            HashMap<String, String> queryMap = new HashMap<>();
            queryMap.put("asin", asin);

            Observable<AmazonProductDetailsResponse> observable = getRepository().getAmazonProductDetails(queryMap)
                    .doOnNext(result -> {
                        products.add(result); // Add result to products list
//                        index.getAndIncrement(); // Increment the index
                    })
                    .doOnError(error -> Log.e("syncBagProducts", "error: " + error.getMessage()));

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
                                    Log.e("syncBagProducts", "Final error: " + error.getMessage());
                                },
                                () -> {
                                    // This is called once all observables have completed
                                    bagProducts.setValue(products);
                                    setIsLoading(false);
                                }
                        )
        );

    }
    public void syncPromoCodes() {
        getCompositeDisposable().add(getRepository().getPromoCode()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                                promoCodes.setValue(result);
                                },
                                error -> {

                                })
        );
        // promo code : list of user possessing
    }
}
