package com.example.shopbee.ui.login;

import android.util.Log;
import android.util.Pair;

import androidx.lifecycle.LifecycleOwner;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.ListOrderResponse;
import com.example.shopbee.data.model.api.OrderResponse;
import com.example.shopbee.data.model.api.UserResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import org.jetbrains.annotations.Async;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class LoginViewModel extends BaseViewModel<LoginNavigator> {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public LoginViewModel(Repository repository) {
        super(repository);
    }
    public void login(String email, String password) {
        Log.d("LoginViewModel", "login called with email: " + email + ", password: " + password);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                getDataResponse(email);
            } else {
                getNavigator().handleError(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    public void loginWithGoogle(AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getNavigator().openMainActivity();
                    } else {
                        getNavigator().handleError(Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }
    public void getDataResponse(String email){
        setIsLoading(true);
        Observable<UserResponse> userInfoObservable = getRepository().getUserInformation(email)
                .subscribeOn(Schedulers.io());

        Observable<ListOrderResponse> orderInfoObservable = getRepository().getListOrderInformation(email)
                .subscribeOn(Schedulers.io());

        getCompositeDisposable().add(Observable.zip(userInfoObservable, orderInfoObservable,
                (userResponse, listOrderResponse) -> new Pair(userResponse, listOrderResponse))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }));
    }
}
