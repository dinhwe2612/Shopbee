package com.example.shopbee.di.module;

import androidx.annotation.NonNull;

import com.example.shopbee.data.model.api.AmazonProductDetailsResponse;
import com.example.shopbee.data.model.api.jsondeserializer.ProductDetailsDeserializer;
import com.example.shopbee.data.remote.AmazonApiService;
import com.example.shopbee.data.remote.CountryApiService;
import com.example.shopbee.data.remote.TexelVirtualTryOnApiService;
import com.example.shopbee.ui.component.bottombar.BottomBarUserReactionImplementation;
import com.example.shopbee.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Provides
    @Singleton
    public static AmazonApiService provideMovieApiService(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        okhttp3.Request request = chain.request();
                        okhttp3.Request newResquest;
                        newResquest = request.newBuilder()
                                .addHeader(NetworkUtils.HEADER_HOST, NetworkUtils.HOST)
                                .addHeader(NetworkUtils.HEADER_KEY, NetworkUtils.KEY)
                                .build();
                        return chain.proceed(newResquest);
                    }
                })
                .build();
        Gson customGson = new GsonBuilder()
                .registerTypeAdapter(AmazonProductDetailsResponse.class, new ProductDetailsDeserializer())
                .create();
        return new Retrofit.Builder()
                .baseUrl(NetworkUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(customGson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(AmazonApiService.class);
    }
    @Provides
    @Singleton
    public static CountryApiService provideCountryService(){
        return new Retrofit.Builder()
                .baseUrl(NetworkUtils.BASE_URL_COUNTRY)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountryApiService.class);
    }
    @Provides
    @Singleton
    public static TexelVirtualTryOnApiService provideTexelVirtualTryOnApiService(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        return new Retrofit.Builder()
                .baseUrl(NetworkUtils.BASE_URL_TEXEL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(TexelVirtualTryOnApiService.class);
    }
    @Provides
    @Singleton
    public static BottomBarUserReactionImplementation provideBottomBarUserReactionImplementation(){
        return new BottomBarUserReactionImplementation();
    }
}
