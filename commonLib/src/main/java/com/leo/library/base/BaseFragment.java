package com.leo.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.leo.library.message.RxBus;
import com.leo.library.mvp.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by leo on 2017/2/17.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected Activity attachedActivity;
    protected String fragmentTag;
    protected Realm realm;
    protected T presenter;
    private List<WeakReference<Subscription>> subscriptions = new ArrayList<>();


    public BaseFragment(String tag) {
        fragmentTag = tag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        attachedActivity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        presenter = createPresenter();
    }

    protected abstract T createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null)
            realm.close();
        releaseRxBus();
    }

    /**
     * 发送消息总线
     *
     * @param tag 消息标记
     * @param obj
     */
    public void rxBusPostMsg(String tag, Object obj) {
        RxBus.getInstance().rxBusPost(tag, obj);
    }

    /**
     * 接受消息回掉
     *
     * @param tag
     * @return
     */
    public void rxBusReceiveMsg(String tag, Action1<Object> action1) {

        WeakReference<Subscription> weakSubscription = new WeakReference<>(RxBus.getInstance()
                .rxBusReceiveMsg(tag)
                .subscribe(action1));
        subscriptions.add(weakSubscription);
    }

    private void releaseRxBus() {
        if (subscriptions.size() > 0) {
            for (WeakReference<Subscription> subscription : subscriptions) {
                subscription.get().unsubscribe();
            }
        }
    }
}
