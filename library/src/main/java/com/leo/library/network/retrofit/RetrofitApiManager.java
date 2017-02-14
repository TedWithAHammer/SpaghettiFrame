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


    public static RetrofitBaseRequestApi getRetrofitBaseApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .callFactory(OkHttpManager.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(RetrofitBaseRequestApi.class);
    }

}
