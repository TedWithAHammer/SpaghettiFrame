package com.leo.library.network.retrofit;

import com.leo.library.network.okhttp.OkHttpManager;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 单BaseUrl模式
 * Created by Leo on 2016/8/20.
 */

public class RetrofitApiManager {
    private static RetrofitApiManager retrofitApiManager;
    private  RetrofitBaseRequestApi retrofitBaseRequestApi;

    private RetrofitApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .callFactory(OkHttpManager.build())
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
