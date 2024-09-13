package com.example.shopbee.ui.leave_feedback;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserVariationResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.example.shopbee.ui.common.dialogs.writereivewdialog.WriteReviewEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LeaveFeedbackViewModel extends BaseViewModel<LeaveFeedbackNavigator> {
    MutableLiveData<List<Boolean>> isReviewedList = new MutableLiveData<>();
    public MutableLiveData<List<Boolean>> getIsReviewedList() {
        return isReviewedList;
    }
    public LeaveFeedbackViewModel(Repository repository) {
        super(repository);
    }
    public void syncIsReviewedList(OrderResponse orderResponse) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().isProductReviewed(orderResponse)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
                                    isReviewedList.setValue(result);
                                    setIsLoading(false);
                                },
                                error -> {
                                    setIsLoading(false);
                                    Log.e("syncIsReviewedList", "syncIsReviewedList: " + error.getMessage());
                                })
        );
    }
    public void saveReview(WriteReviewEvent event) {
        getCompositeDisposable().add(getRepository().saveReviewForUser(event)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            if (result) {

                            }
                        },
                        error -> {

                        })
        );
    }
}
