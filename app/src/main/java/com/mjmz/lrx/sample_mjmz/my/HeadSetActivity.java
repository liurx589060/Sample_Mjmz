package com.mjmz.lrx.sample_mjmz.my;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.mjmz.lrx.sample_mjmz.MyApplication;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.receiver.MediaButtonReceiver;
import com.mjmz.lrx.sample_mjmz.tools.AdbShellUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Administrator on 2017/11/4.
 */

public class HeadSetActivity extends BaseActivity {
    private TextView mStatusTxv;
    private TextView mOperateTxv;

    private AudioManager mAudioManager;
    private ComponentName mComponentName;

    private WindowManager windowManager;
    private View floatView;
    private TextView floatTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headset);

        init();
        addFloatView();
    }

    private void init() {
        mStatusTxv = (TextView) findViewById(R.id.status_txv);
        mOperateTxv = (TextView) findViewById(R.id.operation_txv);

//        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // AudioManager注册一个MediaButton对象
//        mComponentName = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());
//        registerReceiver(headSetReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));

        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        floatView = LayoutInflater.from(this).inflate(R.layout.float_layout,null);
        floatTextView = (TextView) floatView.findViewById(R.id.textView);
        floatView.setFocusableInTouchMode(true);
        floatView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_VOLUME_DOWN:
                        EventBus.getDefault().post("2");
                        String[] search = {
                                "input tap 500 600",// 返回到主界面，数值与按键的对应关系可查阅KeyEvent
                                "input keyevent 79",
//                        "sleep 1",// 等待1秒
//                        "input keyevent 4",
//                        "am start -n com.mjmz.lrx.sample_mjmz/com.mjmz.lrx.sample_mjmz.TabMainActivity",// 打开微信的启动界面，am命令的用法可自行百度、Google
                        };
                        AdbShellUtil.getInstance().execShell(search,false);
                        return true;
                    case KeyEvent.KEYCODE_VOLUME_UP:
                        EventBus.getDefault().post("1");
                        return true;
                    case KeyEvent.KEYCODE_VOLUME_MUTE:
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private final BroadcastReceiver headSetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
                // phone headset plugged
                if (intent.getIntExtra("state", 0) == 1) {
                    // do something
//                  Log.d(TAG, "耳机检测：插入");
//                  Toast.makeText(context, "耳机检测：插入", Toast.LENGTH_SHORT) .show();
                    mAudioManager.registerMediaButtonEventReceiver(mComponentName);
                    mStatusTxv.setText("耳机检测：插入");
                    // phone head unplugged
                } else {
                    // do something
//                  Log.d(TAG, "耳机检测：没有插入");
//                  Toast.makeText(context, "耳机检测：没有插入", Toast.LENGTH_SHORT).show();
                    mAudioManager.unregisterMediaButtonEventReceiver(mComponentName);
                    mStatusTxv.setText("耳机检测：没有插入");
                }
            }
        }
    };

    private void addFloatView() {
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
//        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        mParams.format = PixelFormat.RGBA_8888;
//        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;//窗口的宽和高
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.RIGHT| Gravity. CENTER_VERTICAL; // 调整悬浮窗口至右侧中间
        mParams.x = 0;
        mParams.y = 0;
        windowManager.addView(floatView,mParams);
    }

    private void removeFloatView() {
        windowManager.removeView(floatView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("yy","onTouchEvent-x=" + event.getX() + "  y=" + event.getY());
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(headSetReceiver);
//        mAudioManager.unregisterMediaButtonEventReceiver(mComponentName);
        removeFloatView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                EventBus.getDefault().post("2");
                String[] search = {
                        "input tap 500 600",// 返回到主界面，数值与按键的对应关系可查阅KeyEvent
                        "input keyevent 79",
//                        "sleep 1",// 等待1秒
//                        "input keyevent 4",
//                        "am start -n com.mjmz.lrx.sample_mjmz/com.mjmz.lrx.sample_mjmz.TabMainActivity",// 打开微信的启动界面，am命令的用法可自行百度、Google
                };
                AdbShellUtil.getInstance().execShell(search,false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                EventBus.getDefault().post("1");
                return true;
            case KeyEvent.KEYCODE_VOLUME_MUTE:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * eventBus的消息响应函数
     * @param type
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void keydownEvent(String type) {
        int i = Integer.valueOf(type).intValue();
        int volume = ((MyApplication)getApplication()).getHeadsetBean().getVolume();
        boolean isPause = ((MyApplication)getApplication()).getHeadsetBean().isPause();
        switch (i) {
            case 0:
                mOperateTxv.setText("按下暂停--" + isPause);
                break;
            case 1:
                mOperateTxv.setText("按下音量上键--" + volume);
                break;
            case 2:
                mOperateTxv.setText("按下音量下键--" + volume);
                break;
        }
    }
}
