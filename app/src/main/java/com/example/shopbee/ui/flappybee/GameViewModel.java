package com.example.shopbee.ui.flappybee;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GameViewModel extends BaseViewModel {
    private MutableLiveData<List<PromoCodeResponse>> promoCodes = new MutableLiveData<>();
    private MutableLiveData<List<PromoCodeResponse>> promoCodeOfUser = new MutableLiveData<>();
    private Integer type = -1;
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
                            Log.d("TAG", "syncPromoCodes: " + result.size());
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
                            Log.d("TAG", "syncPromoCodesOfUser: " + result.size());
                        },
                        error ->{

                        })
        );
    }
    public void savePromoCode(Integer score) {
        if (score >= 5 && score < 10){
            type = 0;
        }
        else if (score >= 10 && score < 15){
            type = 1;
        } else if (score >= 15) {
            type = 2;
        }
        getCompositeDisposable().add(Observable.zip(
                    getRepository().getAllPromoCodes(),
                    getRepository().getPromoCode(),
                    (allPromoCodes, promoCode) -> {
                        // Combine results into a Pair, or you can use another data structure
                        return new Pair<>(allPromoCodes, promoCode);
                    })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> {
                List<String> listCodeOfUser = new ArrayList<>();
                for (PromoCodeResponse promoCodeUser : result.second) {
                    listCodeOfUser.add(promoCodeUser.getCode());
                }
                for (PromoCodeResponse promoCode : result.first) {
                    if (promoCode.getName().equals("flappybee")){
                        if (type == 0){ //save flappy voucher 30%
                            if (promoCode.getPercent() == 30 && !listCodeOfUser.contains(promoCode.getCode())){
                                getCompositeDisposable().add(getRepository().savePromoCode(promoCode)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(success ->{
                                            if (success){
                                                Log.d("TAG", "SAVE success");
                                            }
                                        })
                                );
                                break;
                            }
                        } else if (type == 1){ //save flappy voucher 60%
                            if (promoCode.getPercent() == 60 && !listCodeOfUser.contains(promoCode.getCode())){
                                getCompositeDisposable().add(getRepository().savePromoCode(promoCode)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(success ->{
                                            if (success){
                                                Log.d("TAG", "SAVE success");
                                            }
                                        })
                                );
                                break;
                            }
                        } else if (type == 2){
                            if (promoCode.getPercent() == 90 && !listCodeOfUser.contains(promoCode.getCode())){
                                getCompositeDisposable().add(getRepository().savePromoCode(promoCode)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(success ->{
                                            if (success){
                                                Log.d("TAG", "SAVE success");
                                            }
                                        })
                                );
                                break;
                            }
                        }
                    }
                }
            })
        );
    }
}
