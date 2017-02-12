package com.leo.library.Base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2017/2/12.
 */

public class BaseActivityLifeCircleCallback implements Application.ActivityLifecycleCallbacks {
    protected List<Activity> activities=new ArrayList<>();
    public BaseActivityLifeCircleCallback(List<Activity> _activities) {
        activities=_activities;
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
