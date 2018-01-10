package com.mjmz.lrx.sample_mjmz.my;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.customeview.FloatLayoutView;
import com.mjmz.lrx.sample_mjmz.receiver.MediaButtonReceiver;

/**
 * Created by Administrator on 2018/1/7.
 */

public class HeadSetActivity extends BaseActivity{
    private WindowManager wm;
    private FloatLayoutView floatView;
    private MediaButtonReceiver mediaButtonReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headset);

        init();
    }

    private void init() {
        wm = (WindowManager)getApplication().getSystemService(Context.WINDOW_SERVICE);

        addFloatView();
        registMediaButtonReceiver();

//        startAccessibilityService();
    }

    /**
     * 前往设置界面开启服务
     */
    private void startAccessibilityService() {
        new AlertDialog.Builder(this)
                .setTitle("开启辅助功能")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("使用此项功能需要您开启辅助功能")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 隐式调用系统设置界面
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    }
                }).create().show();
    }

    private void registMediaButtonReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonReceiver = new MediaButtonReceiver();
        registerReceiver(mediaButtonReceiver,filter);
    }

    private void unRegistMediaButtonReceiver() {
        if(mediaButtonReceiver != null) {
            unregisterReceiver(mediaButtonReceiver);
        }
    }

    private void addFloatView() {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        //设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
//        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        floatView = (FloatLayoutView) LayoutInflater.from(this).inflate(R.layout.float_layout,null);

        if(floatView.getParent() == null) {
            wm.addView(floatView,wmParams);
        }

        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatView.setVisibility(View.GONE);
            }
        });

        floatView.setFocusableInTouchMode(true);
//        floatView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.e("yy","keyCode=" + keyCode);
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_VOLUME_DOWN:
//                        Log.e("yy","volume down");
//                        break;
//                    case KeyEvent.KEYCODE_VOLUME_UP:
//                        Log.e("yy","volume up");
//                        break;
//                    case KeyEvent.KEYCODE_BACK:
//                        finish();
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private void removeFloatView() {
        if(floatView != null && floatView.getParent() != null) {
            wm.removeView(floatView);
        }
        unRegistMediaButtonReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        removeFloatView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("yy","action=" + event.getAction() + "--X=" + event.getX() + "--Y=" + event.getY());
        return super.onTouchEvent(event);
    }
}
