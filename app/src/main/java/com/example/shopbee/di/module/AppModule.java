package com.example.shopbee.di.module;

import androidx.annotation.NonNull;

import com.example.shopbee.datas.remote.AmazonApiService;
import com.example.shopbee.utils.NetworkUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
        return new Retrofit.Builder()
                .baseUrl(NetworkUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(AmazonApiService.class);
    }
}
