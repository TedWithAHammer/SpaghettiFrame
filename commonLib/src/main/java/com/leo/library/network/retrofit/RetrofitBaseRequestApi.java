package com.leo.library.network.retrofit;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Leo on 2016/8/20.
 */

public interface RetrofitBaseRequestApi {
    @GET("/handle")
    Observable<String> testRetrofit();
}
