package com.leo.library.network.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Leo on 2017/2/13.
 */

public class OkHttpCachedInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //// TODO: 2017/2/13 修改缓存头，预留功能有可能自己实现缓存框架
        return null;
    }
}
