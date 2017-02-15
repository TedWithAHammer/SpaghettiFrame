package com.leo.library.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.leo.library.common.ApplicationInitializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import rx.internal.schedulers.CachedThreadScheduler;

/**
 * Created by Leo on 2017/2/12.
 */

public class BaseApplication extends Application {
    private ApplicationInitializer applicationInitializer = new ApplicationInitializer();
    private BaseActivityLifeCircleCallback baseActivityLifeCircleCallback = new BaseActivityLifeCircleCallback();
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
        applicationInitializer.init(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            registerActivityLifecycleCallbacks(baseActivityLifeCircleCallback);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static Application getInstance(){
        return application;
    }
    /**
     * 获取当前Activity的名字
     *
     * @return
     */
    public String getCurrentActivityName() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            return baseActivityLifeCircleCallback.getCurrentActivity().getLocalClassName();
        else {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            return activityManager.getRunningTasks(1).get(0).topActivity.getShortClassName();
        }
    }

}
