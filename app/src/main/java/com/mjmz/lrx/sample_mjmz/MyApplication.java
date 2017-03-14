package com.mjmz.lrx.sample_mjmz;

import android.app.Application;

import com.example.httpwrapper.HttpRequset;
import com.example.httpwrapper.OkGoHttpExecute;
import com.example.imagewrapper.GlideImageLoader;
import com.example.imagewrapper.ImageWrapper;
import com.mjmz.lrx.sample_mjmz.common.CrashHandler;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化图片加载器
        ImageWrapper.init(new GlideImageLoader());
        //初始化http加载
        HttpRequset.init(new OkGoHttpExecute(this));

        /**
         * 获取日志
         */
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
