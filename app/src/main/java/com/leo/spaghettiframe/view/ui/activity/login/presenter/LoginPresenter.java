package com.leo.spaghettiframe.view.ui.activity.login.presenter;


import com.leo.spaghettiframe.view.ui.activity.login.contract.LoginContracts;
import com.leo.spaghettiframe.view.ui.activity.login.model.LoginRepository;
import com.leo.spaghettiframe.view.ui.activity.login.model.LoginRepositoryImpl;

/**
 * Created by wangliying on 2017/2/28.
 * Des:
 */

public class LoginPresenter extends LoginContracts.LoginPresenter {
    @Override
    protected LoginRepository createRepository() {
        return new LoginRepositoryImpl();
    }

    public void produceData() {
//        dataRepository
    }
}
