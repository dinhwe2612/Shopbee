package com.example.shopbee.ui.review;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductReviewResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ReviewViewModel extends BaseViewModel<ReviewNavigator> {
    public ReviewViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<AmazonProductReviewResponse> productReviews = new MutableLiveData<>();
    public void syncReview(HashMap<String, String> map) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAmazonProductReview(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setIsLoading(false);
                    productReviews.setValue(result);
                }, throwable -> {
                    Log.e("ReviewViewModel", throwable.getMessage());
                })
        );
    }
    public MutableLiveData<AmazonProductReviewResponse> getProductReviews() {
        return productReviews;
    }
}
