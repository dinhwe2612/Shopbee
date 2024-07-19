package com.example.shopbee.ui.main;

import android.graphics.Movie;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.datas.Repository;
import com.example.shopbee.ui.base.BaseViewModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel<MainNavigator> {
    public MainViewModel(Repository repository) {
        super(repository);
    }
    MutableLiveData<List<String>> productNames = new MutableLiveData<>();
    public MutableLiveData<List<String>> getProductNames() {
        return productNames;
    }
    public void getCurrentlySearchProducts(HashMap<String, String> query) {
        getCompositeDisposable().add(
                getRepository().search(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(result -> productNames.setValue(result.getNames()),
                                error -> getNavigator().handleError(error.getMessage()))
        );
    }
}
