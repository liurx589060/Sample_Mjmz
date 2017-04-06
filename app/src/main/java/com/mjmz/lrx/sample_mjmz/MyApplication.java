package com.mjmz.lrx.sample_mjmz;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.lrx.httpwrapper.HttpRequset;
import com.example.lrx.httpwrapper.OkGoHttpExecute;
import com.example.lrx.imagewrapper.GlideImageLoader;
import com.example.lrx.imagewrapper.ImageWrapper;
import com.mjmz.lrx.sample_mjmz.common.CrashHandler;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;
import com.mjmz.lrx.sample_mjmz.db.NotifyInstance;
import com.mjmz.lrx.sample_mjmz.my.MyNotifyActivity;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

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

        //友盟推送设置
        uMengPush();
    }

    /**
     * 友盟推送设置
     */
    private void uMengPush() {
        //友盟推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("yy",deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

        //自定义点击事件
        mPushAgent.setPushIntentServiceClass(MyUmengMessageService.class);
    }
}
