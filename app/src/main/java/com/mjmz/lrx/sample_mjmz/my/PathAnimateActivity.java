package com.mjmz.lrx.sample_mjmz.my;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;

/**
 * Created by liurunxiong on 2017/6/30.
 */

public class PathAnimateActivity extends BaseActivity {
    private Handler mHandler;
    private Runnable runnable1;
    private Runnable runnable2;
    private Runnable runnable3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_path);
        mHandler = new Handler();

        ImageView imageView = (ImageView) findViewById(R.id.image_view);
        Drawable drawable = imageView.getDrawable();
        //AnimatedVectorDrawableCompat实现了Animatable接口
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }

        ImageView business = (ImageView) findViewById(R.id.image_business);
        final Drawable business_drawable = business.getDrawable();
        //AnimatedVectorDrawableCompat实现了Animatable接口
        if (business_drawable instanceof Animatable){
            ((Animatable) business_drawable).start();
            mHandler.postDelayed(runnable1 = new Runnable() {
                @Override
                public void run() {
                    ((Animatable) business_drawable).start();
                    mHandler.postDelayed(this,11*1000);
                }
            },11*1000);
        }

        ImageView bar33 = (ImageView) findViewById(R.id.image_bar33);
        final Drawable bar33_drawable = bar33.getDrawable();
        //AnimatedVectorDrawableCompat实现了Animatable接口
        if (bar33_drawable instanceof Animatable){
            ((Animatable) bar33_drawable).start();
            mHandler.postDelayed(runnable2 = new Runnable() {
                @Override
                public void run() {
                    ((Animatable) bar33_drawable).start();
                    mHandler.postDelayed(this,11*1000);
                }
            },11*1000);
        }

        final ImageView tick = (ImageView) findViewById(R.id.image_tick);
        final Drawable tick_drawable = tick.getDrawable();
        //AnimatedVectorDrawableCompat实现了Animatable接口
        if (tick_drawable instanceof Animatable){
            ((Animatable) tick_drawable).start();
            mHandler.postDelayed(runnable3 = new Runnable() {
                @Override
                public void run() {
                    tick.setImageResource(R.drawable.vector_drawable_tick_anim);
                    Drawable drawable = tick.getDrawable();
                    if(drawable instanceof  Animatable) {
                        ((Animatable) drawable).start();
                    }
                }
            },6*1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable1);
        mHandler.removeCallbacks(runnable2);
        mHandler = null;
    }
}
