package com.leo.library.network.retrofit;

import java.util.IllegalFormatCodePointException;

/**
 * Created by Leo on 2016/8/20.
 */

public class RetrofitApiManager {
    private RetrofitApiManager retrofitApiManager;
    public static Class default_class = RetrofitBaseRequestApi.class;

    public RetrofitApiManager() {

    }

    public RetrofitApiManager getInstance() {
        if (retrofitApiManager == null) {
            retrofitApiManager = new RetrofitApiManager();
        }
        return retrofitApiManager;
    }

    public RetrofitBaseRequestApi getInstance(Class cls) {
        return null;
    }
}
