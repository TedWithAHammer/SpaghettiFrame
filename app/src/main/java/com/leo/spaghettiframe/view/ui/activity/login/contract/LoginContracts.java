package com.leo.spaghettiframe.view.ui.activity.login.contract;

import com.leo.library.mvp.BasePresenter;
import com.leo.library.mvp.BaseView;
import com.leo.spaghettiframe.view.ui.activity.login.model.LoginRepository;

/**
 * Created by wangliying on 2017/2/28.
 * Des:
 */

public interface LoginContracts {
    abstract class LoginPresenter extends BasePresenter<LoginRepository> {

    }

    interface LoginView extends BaseView {

    }


}
