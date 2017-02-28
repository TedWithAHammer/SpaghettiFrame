package com.leo.library.network;

import android.text.TextUtils;
import android.widget.BaseAdapter;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.leo.bulldoglog.BLog;
import com.leo.library.base.BaseApplication;
import com.leo.library.utils.NetworkUtils;

import java.io.IOException;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by leo on 2017/2/14.
 */

public class HttpManager {
    private static final String TAG = "HttpManager";

    public static <T> void rxResponse(Observable<T> observable, final Action1<T> action1) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T t) {
                        //// TODO: 2017/2/14 cached
                        action1.call(t);
                    }
                });
    }

    /**
     * @param observable
     * @param action1 callback
     * @param cacheKey  cache key
     * @param <T>
     */
    public static <T> void rxResponseWithCache(Observable<T> observable, final Action1<T> action1, final String cacheKey) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        BLog.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(T t) {
                        try {
                            DiskLruCacheHelper helper = new DiskLruCacheHelper(BaseApplication.getInstance());
                            if (!TextUtils.isEmpty(cacheKey) && NetworkUtils.isConnected()) {
                                String jsonString = JSON.toJSONString(t);
                                helper.put(cacheKey, jsonString);
                            } else if (!NetworkUtils.isConnected()) {
                                String jsonString = helper.getAsString(cacheKey);
                                if (!TextUtils.isEmpty(jsonString)) {
                                    t = (T) JSON.parse(jsonString);
                                }
                            }
                            action1.call(t);
                        } catch (Exception e) {
                            onError(e);
                        }

                    }
                });
    }

    public static <T> void rxResponse(Observable<T> observable, final Action1<T> action1, Scheduler observeOn) {
        observable.observeOn(observeOn)
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T t) {
                        //// TODO: 2017/2/14 cached
                        action1.call(t);
                    }
                });
    }

    public static <T> void rxResponse(Observable<T> observable, final Action1<T> action1, Scheduler subscribeOn, Scheduler observeOn) {
        observable.observeOn(observeOn)
                .subscribeOn(subscribeOn)
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(T t) {
                        //// TODO: 2017/2/14 cached
                        action1.call(t);
                    }
                });
    }

}
