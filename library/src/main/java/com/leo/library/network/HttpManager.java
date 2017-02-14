package com.leo.library.network;

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
    public static <T> void rxResponse(Observable<T> observable, final Action1<T> action1, Scheduler subscribeOn,Scheduler observeOn) {
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
