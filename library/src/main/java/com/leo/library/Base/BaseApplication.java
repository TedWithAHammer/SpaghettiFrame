package com.leo.library.Base;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.internal.schedulers.CachedThreadScheduler;

/**
 * Created by Leo on 2017/2/12.
 */

public  class BaseApplication extends Application {
    protected ExecutorService cachedThreadScheduler= Executors.newCachedThreadPool();
    @Override
    public void onCreate() {
        super.onCreate();
    }


}
