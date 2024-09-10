package com.example.shopbee.ui.voucher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VoucherViewModel extends BaseViewModel {
    private MutableLiveData<List<PromoCodeResponse>> promoCodes = new MutableLiveData<>();
    public VoucherViewModel(Repository repository) {
        super(repository);
    }

    public MutableLiveData<List<PromoCodeResponse>> getPromoCodes() {
        return promoCodes;
    }
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
    }
}
