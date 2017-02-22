package com.leo.library.network.okhttp;

import com.leo.library.base.BaseApplication;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by leo on 2017/2/14.
 */

public class OkHttpManager {
    private static OkHttpClient okHttpClient;
    private static final String HTTP_CACHED_DIR = BaseApplication.getInstance().getExternalCacheDir() + "/okhttp";
    private static final long CACHED_MAX_SIZE = 10 * 1024 * 1024;
    private static final long CONNECT_TIME_OUT = 10;



    public static OkHttpClient getClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
//                    .cache(new Cache(new File(HTTP_CACHED_DIR), CACHED_MAX_SIZE))
                    //是否需要https验证
//                    .sslSocketFactory(CertificateManager.getSSLSocketFactory())
                    .addInterceptor(new OkHttpLogInterceptor())
                    .build();

        }

        return okHttpClient;
    }


}
