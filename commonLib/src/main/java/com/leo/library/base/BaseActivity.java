package com.leo.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.leo.library.message.RxBus;
import com.leo.library.mvp.BasePresenter;
import com.leo.potato.Potato;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by leo on 2017/2/17.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T presenter;

    protected Realm realm;

    private List<WeakReference<Subscription>> subscriptions = new ArrayList<>();

    private Map<String, WeakReference<BaseFragment>> fragmentRefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflateContentView());
        realm = Realm.getDefaultInstance();
        presenter = createPresenter();
        initActivity();
    }

    protected abstract T createPresenter();

    protected abstract void initActivity();

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Potato.initInjection(this);
    }


    protected abstract int inflateContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseRxBus();
        realm.close();
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
