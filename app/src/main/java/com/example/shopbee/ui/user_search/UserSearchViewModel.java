package com.example.shopbee.ui.user_search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.example.shopbee.ui.search.adapter.ProductAdapter;
import com.example.shopbee.ui.search.adapter.ProductAdapterGridView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserSearchViewModel extends BaseViewModel {
    MutableLiveData<List<String>> suggestions = new MutableLiveData<>();
    // category suggestions
    MutableLiveData<List<String>> searchHistory = new MutableLiveData<>();
    MutableLiveData<List<String>> shortSearchHistory = new MutableLiveData<>();
    MutableLiveData<Boolean> historyIsShort = new MutableLiveData<>();

    public MutableLiveData<List<String>> getSuggestions() {
        return suggestions;
    }

    public void setHistoryIsShort(boolean historyIsShort) {
        this.historyIsShort.setValue(historyIsShort);
    }

    public MutableLiveData<Boolean> getHistoryIsShort() {
        return historyIsShort;
    }

    public UserSearchViewModel(Repository repository) {
        super(repository);
//        getHistoryIsShort().setValue(true);
    }
    public MutableLiveData<List<String>> getShortListOfSearchHistory() {
        return shortSearchHistory;
    }
    public MutableLiveData<List<String>> getFullListOfSearchHistory() {
        return searchHistory;
    }
    public void saveSearchHistory(String searchText) {

    }
    public void syncSearchHistory() {
        setIsLoading(true);
//        getCompositeDisposable().add(getRepository().getUserSearchHistory()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(result -> {
//                            setIsLoading(false);
//                            searchHistory.setValue(result);
//                            shortSearchHistory.setValue(result.subList(0, 5));
//                        },
//                        error -> {
//                            setIsLoading(false);
//                            Log.e("getUserSearchHistory", "getUserSearchHistory " + error.getMessage());
//                        })
//        );
    }
    public void syncSuggestions() {
        setIsLoading(true);
    }
}
