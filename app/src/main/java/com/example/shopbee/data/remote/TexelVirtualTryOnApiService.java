package com.example.shopbee.data.remote;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface TexelVirtualTryOnApiService {
    @Headers({
            "x-rapidapi-key: fd86ddec9fmshdf29963c5727ee2p174966jsn5dc25f52146f",
            "x-rapidapi-host: texel-virtual-try-on.p.rapidapi.com",
    })
    @Multipart
    @POST("/try-on-url")
    Observable<ResponseBody> tryOn(@Part("clothing_image_url") RequestBody clothingImageUrl,
                                   @Part("avatar_image_url") RequestBody avatarImageUrl);
}