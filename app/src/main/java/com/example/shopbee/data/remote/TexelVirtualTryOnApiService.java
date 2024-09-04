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
            "x-rapidapi-key: 9be58ff088msh3ba71c036a6b06ep10c081jsn8a800bf06a92",
            "x-rapidapi-host: texel-virtual-try-on.p.rapidapi.com",
    })
    @Multipart
    @POST("/try-on-url")
    Observable<ResponseBody> tryOn(@Part("clothing_image_url") RequestBody clothingImageUrl,
                                   @Part("avatar_image_url") RequestBody avatarImageUrl);
}