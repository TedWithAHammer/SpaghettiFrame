package com.leo.library.common;

import android.app.Application;


import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.moduth.blockcanary.BlockCanary;
import com.leo.bulldoglog.BLog;
import com.leo.library.monitor.AppBlockCanaryContext;
import com.squareup.leakcanary.LeakCanary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Leo on 2017/2/12.
 */

public class ApplicationInitializer {
    private ExecutorService cachedThreadScheduler = Executors.newCachedThreadPool();
    private static final String DB_NAME = "default.realm";

    /**
     * 并行初始化
     *
     * @param application
     */
    public void init(final Application application) {
        /* 因为接下来立即使用了该包需同步初始化 */
        BLog.init();
        /* 其他初始化任务无时序要求可多线程处理 */
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {
                Realm.init(application);
                RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                        .name(DB_NAME)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                Realm.setDefaultConfiguration(realmConfiguration);
            }
        });
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {
                Fresco.initialize(application);
            }
        });
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {
//                LeakCanary.install(application);
            }
        });
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {
//                BlockCanary.install(application,new AppBlockCanaryContext()).start();
            }
        });
        cachedThreadScheduler.execute(new Runnable() {
            @Override
            public void run() {
                ARouter.init(application);
            }
        });
    }
}
