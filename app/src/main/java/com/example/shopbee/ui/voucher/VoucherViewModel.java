package com.example.shopbee.ui.voucher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.PromoCodeResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VoucherViewModel extends BaseViewModel {
    private MutableLiveData<List<PromoCodeResponse>> promoCodes = new MutableLiveData<>();
    private MutableLiveData<List<PromoCodeResponse>> promoCodeOfUser = new MutableLiveData<>();
    private MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();

    public VoucherViewModel(Repository repository) {
        super(repository);
        repository.getUserResponse().observeForever(new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse response) {
                userResponse.setValue(response);
            }
        });
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
    public MutableLiveData<UserResponse> getUserResponse() {
        return userResponse;
    }
    public void setValuePromoCodeUser(List<PromoCodeResponse> listPromoCodeResponse){
        promoCodeOfUser.setValue(listPromoCodeResponse);
    }
}
