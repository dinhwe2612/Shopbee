package com.example.shopbee.ui.buy_now;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.OrderDetailResponse;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.ui.bag.BagNavigator;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BuyNowViewModel extends BaseViewModel<BuyNowNavigator> {
    MutableLiveData<List<PromoCodeResponse>> promoCodes = new MutableLiveData<>();

    public MutableLiveData<List<PromoCodeResponse>> getPromoCodes() {
        return promoCodes;
    }

    public BuyNowViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<List<OrderDetailResponse>> bagProducts = new MutableLiveData<>();

    public MutableLiveData<List<OrderDetailResponse>> getBagProducts() {
        return bagProducts;
    }

    public void getBagVariationFromFirebase() {

    }

//    public void syncBagProducts(List<String> bagLists) {
//        setIsLoading(true);
//        // get information from Amazon asin
//        // reload adapter many times?
//        List<AmazonProductDetailsResponse> products = new ArrayList<>();
//        if (products.size() == bagLists.size()) {
//            setIsLoading(false);
//            return;
//        }
////        AtomicInteger index = new AtomicInteger();
//        List<Observable<AmazonProductDetailsResponse>> observables = new ArrayList<>();
//
//        for (String asin : bagLists) {
//            HashMap<String, String> queryMap = new HashMap<>();
//            queryMap.put("asin", asin);
//
//            Observable<AmazonProductDetailsResponse> observable = getRepository().getAmazonProductDetails(queryMap)
//                    .doOnNext(result -> {
//                        products.add(result); // Add result to products list
////                        index.getAndIncrement(); // Increment the index
//                    })
//                    .doOnError(error -> Log.e("syncBagProducts", "error: " + error.getMessage()));
//
//            observables.add(observable);
//        }
//
//// Use concatMap to ensure serial execution of the observables
//        getCompositeDisposable().add(
//                Observable.concat(observables)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                result -> {
//                                    // You could handle each result here if needed
//                                },
//                                error -> {
//                                    Log.e("syncBagProducts", "Final error: " + error.getMessage());
//                                },
//                                () -> {
//                                    // This is called once all observables have completed
//                                    bagProducts.setValue(products);
//                                    setIsLoading(false);
//                                }
//                        )
//        );
//
//    }
//    public void syncBagProducts() {
//        setIsLoading(true);
//        getCompositeDisposable().add(getRepository().getUserVariation(Repository.UserVariation.BAG)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> {
//                            bagProducts.setValue(result);
//                            setIsLoading(false);
//                        },
//                        error -> {
//                            setIsLoading(false);
//                        })
//        );
//    }
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
