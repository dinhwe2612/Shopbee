package com.example.shopbee.ui.my_vouchers;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyVouchersViewModel extends BaseViewModel<MyVouchersNavigator> {
    MutableLiveData<List<PromoCodeResponse>> promoCodeResponseList = new MutableLiveData<>();
    public MyVouchersViewModel(Repository repository) {
        super(repository);
    }

    public MutableLiveData<List<PromoCodeResponse>> getPromoCodeResponseList() {
        return promoCodeResponseList;
    }
    public void syncPromoCodes() {
        getCompositeDisposable().add(getRepository().getPromoCode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            promoCodeResponseList.setValue(result);
                            setIsLoading(false);
                        },
                        error -> {
                            setIsLoading(false);
                        })
        );
    }
}
