package com.leo.library.common;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leo on 2017/2/12.
 */

public class ApplicationInitializer {
    protected ExecutorService cachedThreadScheduler= Executors.newCachedThreadPool();

    public void init(Application application){
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
