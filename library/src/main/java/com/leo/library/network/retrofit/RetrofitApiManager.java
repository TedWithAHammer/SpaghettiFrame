package com.leo.library.network.retrofit;

import com.leo.library.network.okhttp.OkhttpRequestInterceptor;
import com.leo.library.network.okhttp.OkhttpResponseInterceptor;

import java.util.IllegalFormatCodePointException;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 单BaseUrl模式
 * Created by Leo on 2016/8/20.
 */

public class RetrofitApiManager {
    private static RetrofitApiManager retrofitApiManager;
    private final RetrofitBaseRequestApi retrofitBaseRequestApi;

    public RetrofitApiManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                /*添加 Application interceptor(请求) */
                .addInterceptor(new OkhttpRequestInterceptor())
                /*添加 Network interceptor(回应) */
                .addNetworkInterceptor(new OkhttpResponseInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .callFactory(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        retrofitBaseRequestApi = retrofit.create(RetrofitBaseRequestApi.class);
    }

    public static RetrofitApiManager getInstance() {
        if (retrofitApiManager == null) {
            retrofitApiManager = new RetrofitApiManager();
        }
        return retrofitApiManager;
    }



}
