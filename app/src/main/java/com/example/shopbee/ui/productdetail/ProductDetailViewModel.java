package com.example.shopbee.ui.productdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.shopbee.data.Repository;
import com.example.shopbee.data.model.api.AmazonProductByCategoryResponse;
import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.ui.common.base.BaseViewModel;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductDetailViewModel extends BaseViewModel<ProductDetailNavigator> {
    MutableLiveData<AmazonProductDetailsResponse> productDetails = new MutableLiveData<>();
    MutableLiveData<AmazonProductByCategoryResponse> productByCategory = new MutableLiveData<>();
    MutableLiveData<Bitmap> tryOnImage = new MutableLiveData<>();
    public ProductDetailViewModel(Repository repository) {
        super(repository);
    }
    public void syncProductDetails(HashMap<String, String> query) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAmazonProductDetails(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setIsLoading(false);
                    productDetails.setValue(result);
                }, error -> {
                    setIsLoading(false);
                    Log.e("syncProductDetails", "error on getAmazonProductDetails: " + error.getMessage());
                })
        );
    }
    public void syncProductByCategory(HashMap<String, String> query) {
        setIsLoading(true);
        getCompositeDisposable().add(getRepository().getAmazonProductByCategory(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    setIsLoading(false);
                    productByCategory.setValue(result);
                }, error -> {
                    setIsLoading(false);
                    Log.e("syncProductDetails", "error on getAmazonProductByCategory: " + error.getMessage());
                })
        );
    }
    public void syncTryOnImage(Bitmap personBitmap, String garmentUrl) {
        getCompositeDisposable().add(getRepository().getTryOnImage(personBitmap, garmentUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    tryOnImage.setValue(BitmapFactory.decodeStream(result.byteStream()));
                }, error -> {
                    Log.e("syncTryOnImage", "error on getTryOnImage: " + error.getMessage());
                })
        );
    }
    public MutableLiveData<AmazonProductDetailsResponse> getProductDetails() {
        return productDetails;
    }
    public MutableLiveData<AmazonProductByCategoryResponse> getProductByCategory() {
        return productByCategory;
    }
    public MutableLiveData<Bitmap> getTryOnImage() {
        return tryOnImage;
    }
}
