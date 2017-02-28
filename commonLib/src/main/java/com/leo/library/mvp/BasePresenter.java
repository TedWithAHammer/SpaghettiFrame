package com.leo.library.mvp;

/**
 * Created by wangliying on 2017/2/28.
 * Des:
 */

public abstract class BasePresenter<T> {
    protected T dataRepository;

    public BasePresenter() {
        dataRepository = createRepository();
    }

    protected abstract T createRepository();
}
