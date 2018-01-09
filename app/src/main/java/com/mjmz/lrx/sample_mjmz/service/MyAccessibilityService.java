package com.mjmz.lrx.sample_mjmz.service;

import android.accessibilityservice.AccessibilityService;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.mjmz.lrx.sample_mjmz.tools.AdbShellUtil;

/**
 * Created by Administrator on 2018/1/7.
 */

public class MyAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        Log.e("yy","onAccessibilityEvent");
    }

    @Override
    public void onInterrupt() {
        Log.e("yy","onInterrupt");
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.e("yy","volume down");
                String[] search = {
                        "input swipe 10 200 500 200",// 返回到主界面，数值与按键的对应关系可查阅KeyEvent
//                        "input keyevent 5",
//                        "sleep 1",// 等待1秒
//                        "input keyevent 4",
//                        "am start -n com.mjmz.lrx.sample_mjmz/com.mjmz.lrx.sample_mjmz.TabMainActivity",// 打开微信的启动界面，am命令的用法可自行百度、Google
                };
                AdbShellUtil.getInstance().execShell(search,false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.e("yy","volume up");
                return true;
        }
        return super.onKeyEvent(event);
    }
}
