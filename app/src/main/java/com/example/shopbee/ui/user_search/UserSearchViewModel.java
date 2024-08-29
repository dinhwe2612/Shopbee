package com.example.shopbee.ui.user_search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserSearchViewModel extends BaseViewModel {
    MutableLiveData<List<String>> searchHistory = new MutableLiveData<>();
    public UserSearchViewModel(Repository repository) {
        super(repository);
    }
    public MutableLiveData<List<String>> getShortListOfSearchHistory() {
        // get 5 first strings of search history
        MutableLiveData<List<String>> shortList = new MutableLiveData<>();
        shortList.setValue(searchHistory.getValue().subList(0, 5));
        return shortList;
    }
    public MutableLiveData<List<String>> getFullListOfSearchHistory() {
        return searchHistory;
    }
    public void saveSearchHistory(String searchText) {

    }
    public void syncSearchHistory() {
//        setIsLoading(true);
//        getCompositeDisposable().add(getRepository().getAmazonProductByCategory(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> {
//                            setIsLoading(false);
//                            categoryProducts.setValue(result.getData().getProducts());
//                        },
//                        error -> {
//                            setIsLoading(false);
//                            Log.e("getAmazonProductByCategory", "getAmazonProductByCategory " + error.getMessage());
//                        })
//        );
    }
}
