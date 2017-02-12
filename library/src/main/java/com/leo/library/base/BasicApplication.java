package com.leo.library.base;

import android.app.Application;

import com.leo.bulldoglog.BLog;
import com.leo.library.base.lifecycle.ApplicationLifeCycleHelper;

/**
 * Created by leo on 2017/1/9.
 */

public class BasicApplication extends Application {
    private static final String Tag = "SpaghettiFrame";
    private static BasicApplication instance;
    private ApplicationLifeCycleHelper lifeCycleHelper = new ApplicationLifeCycleHelper();


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        lifeCycleHelper.init(this);
    }

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
