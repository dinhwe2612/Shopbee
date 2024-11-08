package com.example.shopbee.ui.user_search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserSearchViewModel extends BaseViewModel {
    MutableLiveData<Boolean> isUserNotSignedIn = new MutableLiveData<>();
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
        getRepository().saveSearchHistory(searchText);
    }
    public void syncSearchHistory() {
//        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getSearchHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
//                            setIsLoading(false);
//                            if (result == null) {
//                                isUserNotSignedIn.setValue(true);
//                                return;
//                            }
                            searchHistory.setValue(result.getHistorySearch());
                            if (result.getHistorySearch().size() == 0) {
                                shortSearchHistory.setValue(new ArrayList<>());
                            }
                            else {
                                int endIndex = Math.min(result.getHistorySearch().size(), 5);
                                shortSearchHistory.setValue(result.getHistorySearch().subList(0, endIndex));
                            }
                        },
                        error -> {
//                            setIsLoading(false);
                            Log.e("getUserSearchHistory", "getUserSearchHistory " + error.getMessage());
                        })
        );
    }

    public Observable<String> deleteSearchHistory(String searchText) {
        return Observable.create(emitter -> {
//            setIsLoading(true);
            getCompositeDisposable().add(getRepository().deleteSearchHistory(searchText)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {
//                                setIsLoading(false);
                                Log.e("deleteSearchHistory", "deleteSearchHistory " + result);
                                if (result.equals("complete")) {
//                                    syncSearchHistory();
                                    emitter.onNext("complete");
                                    emitter.onComplete();
                                }
                            },
                            error -> {
//                                setIsLoading(false);
                            })
            );
        });
    }
    public void syncSuggestions() {
//        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAllUserSearchHistory()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> {
//                                    setIsLoading(false);
//                            if (result == null) {
//                                isUserNotSignedIn.setValue(true);
//                                return;
//                            }
                                    Log.e("Suggestions", "getUserSearchHistory " + result);
                                    suggestions.setValue(result);
                                },
                                error -> {
//                                    setIsLoading(false);
                                    Log.e("getUserSearchHistory", "getUserSearchHistory " + error.getMessage());
                                })
        );
    }
}
