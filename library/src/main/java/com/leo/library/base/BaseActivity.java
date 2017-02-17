package com.leo.library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.leo.potato.Potato;
import com.leo.potato.PotatoInjection;

import java.lang.ref.WeakReference;
import java.util.Map;

import io.realm.Realm;

/**
 * Created by leo on 2017/2/17.
 */

public abstract class BaseActivity extends Activity {

    protected Realm realm;

//    private Map<String, WeakReference<ABaseFragment>> fragmentRefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(inflateContentView());
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Potato.initInjection(this);
    }


    protected abstract int inflateContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
