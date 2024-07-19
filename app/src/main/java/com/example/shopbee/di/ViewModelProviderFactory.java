package com.example.shopbee.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.function.Supplier;

public class ViewModelProviderFactory<T extends ViewModel> extends ViewModelProvider.NewInstanceFactory {
    final Class<T> viewModelClass;
    final Supplier<T> viewModelSupplier;

    public ViewModelProviderFactory(Class<T> viewModelClass, Supplier<T> viewModelSupplier) {
        this.viewModelClass = viewModelClass;
        this.viewModelSupplier = viewModelSupplier;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            return (T)viewModelSupplier.get();
        } else {
            throw new IllegalArgumentException("Unknow class name " + viewModelClass.getName());
        }
    }
}
