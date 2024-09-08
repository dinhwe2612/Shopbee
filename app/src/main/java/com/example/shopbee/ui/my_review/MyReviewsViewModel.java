package com.example.shopbee.ui.my_review;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.example.shopbee.ui.common.dialogs.writereivewdialog.WriteReviewEvent;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyReviewsViewModel extends BaseViewModel<MyReviewNavigator> {
    MutableLiveData<List<WriteReviewEvent>> reviewList = new MutableLiveData<>();
    public MutableLiveData<List<WriteReviewEvent>> getReviewList() {
        return reviewList;
    }
    public MyReviewsViewModel(Repository repository) {
        super(repository);
    }
    public void syncReviewList() {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getReviewsForUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            reviewList.setValue(result);
                            setIsLoading(false);
                        },
                        error -> {
                            setIsLoading(false);
                            Log.e("syncReviewList", "syncReviewList: " + error.getMessage());
                        })
        );
    }
}
