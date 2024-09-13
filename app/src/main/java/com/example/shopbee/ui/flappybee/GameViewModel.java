package com.example.shopbee.ui.flappybee;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GameViewModel extends BaseViewModel {
    private MutableLiveData<List<PromoCodeResponse>> promoCodes = new MutableLiveData<>();
    private MutableLiveData<List<PromoCodeResponse>> promoCodeOfUser = new MutableLiveData<>();

    public GameViewModel(Repository repository) {
        super(repository);
    }
    public MutableLiveData<List<PromoCodeResponse>> getPromoCodes() {
        return promoCodes;
    }
    public MutableLiveData<List<PromoCodeResponse>> getPromoCodeOfUser() {
        return promoCodeOfUser;
    }
    public void syncPromoCodes() {
        getCompositeDisposable().add(getRepository().getAllPromoCodes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            promoCodes.setValue(result);
                        },
                        error -> {

                        })
        );
    }
    public void syncPromoCodesOfUser() {
        getCompositeDisposable().add(getRepository().getPromoCode()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            promoCodeOfUser.setValue(result);
                        },
                        error ->{

                        })
        );
    }
}
