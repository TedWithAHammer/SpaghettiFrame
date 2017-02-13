package com.leo.spaghettiframe.view.ui.activity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.leo.spaghettiframe.R;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import static android.content.pm.PackageManager.GET_SERVICES;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public static boolean isInServiceProcess(Context context, Class<? extends Service> serviceClass) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), GET_SERVICES);
        } catch (Exception e) {
            Log.e("AndroidUtils", "Could not get package info for " + context.getPackageName(), e);
            return false;
        }
        String mainProcess = packageInfo.applicationInfo.processName;

        ComponentName component = new ComponentName(context, serviceClass);
        ServiceInfo serviceInfo;
        try {
            serviceInfo = packageManager.getServiceInfo(component, 0);
        } catch (PackageManager.NameNotFoundException ignored) {
            // Service is disabled.
            return false;
        }
        if (serviceInfo.processName.equals(mainProcess)) {
            Log.e("AndroidUtils",
                    "Did not expect service " + serviceClass + " to run in main process " + mainProcess);
            // Technically we are in the service process, but we're not in the service dedicated process.
            return false;

        }

        //查找当前进程名
        int myPid = android.os.Process.myPid();
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningAppProcessInfo myProcess = null;
        for (ActivityManager.RunningAppProcessInfo process : activityManager.getRunningAppProcesses()) {
            if (process.pid == myPid) {
                myProcess = process;
                break;
            }
        }
        if (myProcess == null) {
            Log.e("AndroidUtils", "Could not find running process for " + myPid);
            return false;
        }

        return myProcess.processName.equals(serviceInfo.processName);
    }
}
