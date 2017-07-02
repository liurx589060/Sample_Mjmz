package com.mjmz.lrx.sample_mjmz.my;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;

/**
 * Created by liurunxiong on 2017/6/30.
 */

public class PathAnimateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_path);

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
            final Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
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
            final Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((Animatable) bar33_drawable).start();
                    mHandler.postDelayed(this,11*1000);
                }
            },11*1000);
        }
    }
}
