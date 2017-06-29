package com.mjmz.lrx.sample_mjmz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.example.lrx.httpwrapper.HttpRequset;
import com.example.lrx.httpwrapper.httpexecute.DefaultHttpExecute;
import com.example.lrx.imagewrapper.DefaultImageLoader;
import com.example.lrx.imagewrapper.ImageWrapper;
import com.lzy.okgo.OkGo;
import com.mjmz.lrx.sample_mjmz.common.Const;
import com.mjmz.lrx.sample_mjmz.common.CrashHandler;
import com.mjmz.lrx.sample_mjmz.language.StringUtil;
import com.mjmz.lrx.sample_mjmz.tools.GlobalToolsUtil;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化图片加载器
        ImageWrapper.init(new DefaultImageLoader());
        //初始化http加载
        HttpRequset.init(new HttpExecute(this));

        /**
         * 获取日志
         */
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        //友盟推送设置
        uMengPush();

        //内存泄漏tiaos调试检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
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

        //设置语言
        SharedPreferences sp = GlobalToolsUtil.getSharedPreferences(this);
        StringUtil.switchLanguage(this,sp.getInt(Const.SP_LANGUAGE,0));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
