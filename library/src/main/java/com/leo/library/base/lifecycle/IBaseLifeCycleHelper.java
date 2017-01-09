package com.leo.library.base.lifecycle;

import android.content.Context;

/**
 * Created by leo on 2017/1/9.
 */

public interface IBaseLifeCycleHelper {
    void init(Context context);

    void onCreate();

    void onDestroy();
}
