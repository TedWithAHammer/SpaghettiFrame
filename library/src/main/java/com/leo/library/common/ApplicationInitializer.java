package com.leo.library.common;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.leo.bulldoglog.BLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Leo on 2017/2/12.
 */

public class ApplicationInitializer {
    protected ExecutorService cachedThreadScheduler= Executors.newCachedThreadPool();

    /**
     * 并行初始化
     * @param application
     */
    public void init(final Application application){
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {
                BLog.init();
            }
        });
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {
                Fresco.initialize(application);
            }
        });
    }
}
