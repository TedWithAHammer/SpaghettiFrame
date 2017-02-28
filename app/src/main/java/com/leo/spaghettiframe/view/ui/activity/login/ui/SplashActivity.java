package com.leo.spaghettiframe.view.ui.activity.login.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.leo.library.base.BaseActivity;
import com.leo.library.mvp.BasePresenter;
import com.leo.potato.PotatoInjection;
import com.leo.spaghettiframe.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    ViewPager vpMain;
    List<WeakReference<Bitmap>> refBitmaps = new ArrayList<>();
    BitmapAdapter bitmapAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        vpMain = (ViewPager) findViewById(R.id.vp_main);
        initVPAdapter();

    }

    private void initVPAdapter() {
        try {
            InputStream is1 = getAssets().open("img_splash1.jpg");
            Bitmap bmp1 = BitmapFactory.decodeStream(is1);
            InputStream is2 = getAssets().open("img_splash2.jpg");
            Bitmap bmp2 = BitmapFactory.decodeStream(is2);
            InputStream is3 = getAssets().open("img_splash3.jpg");
            Bitmap bmp3 = BitmapFactory.decodeStream(is3);
            WeakReference<Bitmap> ref1 = new WeakReference<Bitmap>(bmp1);
            WeakReference<Bitmap> ref2 = new WeakReference<Bitmap>(bmp2);
            WeakReference<Bitmap> ref3 = new WeakReference<Bitmap>(bmp3);
            refBitmaps.add(ref1);
            refBitmaps.add(ref2);
            refBitmaps.add(ref3);
            bitmapAdapter = new BitmapAdapter();
            vpMain.setAdapter(bitmapAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class BitmapAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return refBitmaps.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
