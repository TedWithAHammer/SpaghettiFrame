package com.leo.spaghettiframe.view.ui.activity.login.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.leo.library.base.BaseActivity;
import com.leo.library.mvp.BasePresenter;
import com.leo.potato.PotatoInjection;
import com.leo.spaghettiframe.R;

public class SplashActivity extends BaseActivity {
    @PotatoInjection(idStr = "vp_main")
    ViewPager vpMain;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initActivity() {
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected int inflateContentView() {
        return R.layout.activity_splash;
    }
}
