package com.leo.library.network.okhttp;

import com.leo.bulldoglog.BLog;
import com.leo.library.utils.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Leo on 2016/8/20.
 */

public class OkHttpLogInterceptor implements Interceptor {
    private static final String TAG = "OkHttpLogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        BLog.i(TAG, "Time: " + DateUtils.timeMills2DateString(System.nanoTime(), DateUtils.PATTERN1) + "\n"
                + "Request Info:" + request.url() + "\n" + "Headers:" + request.headers());
        String responseString = new BufferedReader(new InputStreamReader(response.body().byteStream())).readLine();
        BLog.i(TAG, "Time: " + DateUtils.timeMills2DateString(System.nanoTime(), DateUtils.PATTERN1) + "\n"
                + "Response Info:" + responseString + "\n" + "Headers:" + request.headers());
        return response;
    }
}
