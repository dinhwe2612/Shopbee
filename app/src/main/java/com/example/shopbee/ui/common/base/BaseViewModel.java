package com.example.shopbee.ui.common.base;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopbee.data.Repository;

import java.lang.ref.WeakReference;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends ViewModel {
    final Repository repository;
    MutableLiveData<Boolean> inProgress = new MutableLiveData<>();
    final ObservableBoolean loading = new ObservableBoolean();
    final CompositeDisposable disposable;
    WeakReference<N> navigator;
    public BaseViewModel(Repository repository) {
        this.repository = repository;
        this.disposable = new CompositeDisposable();
    }
    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
    public CompositeDisposable getCompositeDisposable() {
        return disposable;
    }
    public Repository getRepository() {
        return repository;
    }
    public ObservableBoolean getIsLoading() {
        return loading;
    }
    public void setIsLoading(boolean isLoading) {
        loading.set(isLoading);
        inProgress.setValue(isLoading);
    }

//    public void setInProgress(boolean isLoading) {
//        inProgress.setValue(isLoading);
//    }
    public MutableLiveData<Boolean> getInProgress() {
        return inProgress;
    }

    public N getNavigator() {
        return navigator.get();
    }
    public void setNavigator(N navigator) {
        this.navigator = new WeakReference<>(navigator);
    }
}
