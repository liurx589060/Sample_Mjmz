package com.mjmz.lrx.sample_mjmz;

import android.app.Application;

import com.example.imagewrapper.GlideImageLoader;
import com.example.imagewrapper.ImageWrapper;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化图片加载器
        ImageWrapper.init(new GlideImageLoader());
    }
}
