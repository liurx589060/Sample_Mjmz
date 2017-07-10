package com.mjmz.lrx.sample_mjmz.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mjmz.lrx.sample_mjmz.language.ViewUtil;
import com.mjmz.lrx.sample_mjmz.tools.GlobalToolsUtil;
import com.mjmz.lrx.sample_mjmz.tools.PermissionUtil;
import com.umeng.message.PushAgent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class BaseFragmentActivity extends FragmentActivity implements PermissionListener{
    private CompositeDisposable compositeDisposable;

    public void addDisposeable(Disposable disposable) {
        if(compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public void dispose() {
        if(compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();

        //注册消息总线
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //防止InputMethodManager造成内存泄漏
        GlobalToolsUtil.fixInputMethodManagerLeak(this);

        //反注册消息总线
        EventBus.getDefault().unregister(this);

        dispose();
    }

    /**
     * eventBus的消息响应函数
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void swithLanguage(String event) {
        if(event.equals("swith language")) {
            //更新控件
            ViewUtil.updateViewLanguage(findViewById(android.R.id.content));
        }
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 转给AndPermission分析结果。
         *
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         * @param listener PermissionListener 对象。
         */
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
