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
        /* 因为接下来立即使用了该包需同步初始化 */
        BLog.init();
        /* 其他初始化任务无时序要求可多线程处理 */
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {

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
