package com.leo.library.base.lifecycle;

import android.app.Application;
import android.content.Context;

import com.leo.bulldoglog.BLog;

/**
 * Created by leo on 2017/1/9.
 */

public class ApplicationLifeCycleHelper implements IBaseLifeCycleHelper {
    private static final String Tag = "SpaghettiFrame";
    private Context applicationContext;

    @Override
    public void init(Context context) {
        applicationContext = context;
    }

    @Override
    public void onCreate() {
        BLog.init(Tag);

    }

    @Override
    public void onDestroy() {

    }

    public void onTerminate() {

    }
}
