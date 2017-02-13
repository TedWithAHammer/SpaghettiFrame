package com.leo.library.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.leo.bulldoglog.BLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2017/2/12.
 */

public class BaseActivityLifeCircleCallback implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "BaseActivityLifeCircleCallback";
    protected List<Activity> activities = new ArrayList<>();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        BLog.i(TAG, "onActivityCreated:" + activity.getLocalClassName());
        activities.add(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        BLog.i(TAG, "onActivityStarted:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        BLog.i(TAG, "onActivityResumed:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        BLog.i(TAG, "onActivityPaused:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        BLog.i(TAG, "onActivityStopped:" + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        BLog.i(TAG, "onActivitySaveInstanceState:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        BLog.i(TAG,"onActivityDestroyed"+activity.getLocalClassName());
        activities.remove(activity);
    }

    public Activity getCurrentActivity(){
        return activities.get(activities.size()-1);
    }
}
