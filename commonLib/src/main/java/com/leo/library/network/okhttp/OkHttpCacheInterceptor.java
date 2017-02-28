package com.leo.library.network.okhttp;

import com.leo.library.base.BaseActivity;
import com.leo.library.base.BaseApplication;
import com.leo.library.network.DiskLruCacheHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.DiskLruCache;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Leo on 2017/2/13.
 */

public class OkHttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //// TODO: 2017/2/13 功能逻辑待完善
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (!request.headers().get("cache-control").equals("no-cache")) {
            BufferedSource bufferedSource = Okio.buffer(response.body().source());
            String key = request.url().toString();
            String cacheContent = bufferedSource.readUtf8();
            DiskLruCacheHelper diskLruCacheHelper = new DiskLruCacheHelper(BaseApplication.getInstance());
            diskLruCacheHelper.put(key, cacheContent);

        }
        return response;
    }
}
