package com.example.modeso_mmac.rxjavaexample.services;

import com.example.modeso_mmac.rxjavaexample.errorhandling.RxErrorHandlingCallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Belal Mohamed on 7/19/16.
 * www.modeso.ch
 */
public class RetrofitServiceGenerator {

    public static final String API_BASE_URL = "http://192.168.1.25:3000/api/";

    private static OkHttpClient.Builder mHttpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder mRetrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create());

    public static <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = mRetrofitBuilder.client(mHttpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
