package com.mjmz.lrx.sample_mjmz.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.EventLog;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.mjmz.lrx.sample_mjmz.MyApplication;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/11/4.
 */

public class MediaButtonReceiver extends BroadcastReceiver {
    private static String TAG = "MediaButtonReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        MyApplication application = (MyApplication) context.getApplicationContext();

        // 获得Action
        String intentAction = intent.getAction();
        // 获得KeyEvent对象
        KeyEvent keyEvent = (KeyEvent) intent
                .getParcelableExtra(Intent.EXTRA_KEY_EVENT);

        Log.e(TAG, "Action ---->" + intentAction + "  KeyEvent----->"
                + keyEvent.toString());
        // 按下 / 松开 按钮
        int keyAction = keyEvent.getAction();

        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)
                && (KeyEvent.ACTION_DOWN == keyAction)) {
            // 获得按键字节码
            int keyCode = keyEvent.getKeyCode();

            // 获得事件的时间
            // long downtime = keyEvent.getEventTime();

            // 获取按键码 keyCode
//          StringBuilder sb = new StringBuilder();
//          // 这些都是可能的按键码 ， 打印出来用户按下的键
//          if (KeyEvent.KEYCODE_MEDIA_NEXT == keyCode) {
//              sb.append("KEYCODE_MEDIA_NEXT");
//          }
            // 说明：当我们按下MEDIA_BUTTON中间按钮时，实际出发的是 KEYCODE_HEADSETHOOK 而不是
            // KEYCODE_MEDIA_PLAY_PAUSE
            if (KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE == keyCode) {
//              sb.append("KEYCODE_MEDIA_PLAY_PAUSE");
            }
            if (KeyEvent.KEYCODE_HEADSETHOOK == keyCode) {
//              sb.append("KEYCODE_HEADSETHOOK");
                application.getHeadsetBean().setPause(!application.getHeadsetBean().isPause());
                EventBus.getDefault().post("0");
                Log.e("yy","HEADSETHOOK");
            }
            if (KeyEvent.KEYCODE_MEDIA_PREVIOUS == keyCode) {
//              sb.append("KEYCODE_MEDIA_PREVIOUS");
            }
            if (KeyEvent.KEYCODE_MEDIA_STOP == keyCode) {
//              sb.append("KEYCODE_MEDIA_STOP");
            }

            if (KeyEvent.KEYCODE_VOLUME_UP == keyCode) {
//              sb.append("KEYCODE_MEDIA_STOP");
                application.getHeadsetBean().setVolume(application.getHeadsetBean().getVolume() + 1);
                EventBus.getDefault().post("1");
            }

            if (KeyEvent.KEYCODE_VOLUME_DOWN == keyCode) {
//              sb.append("KEYCODE_MEDIA_STOP");
                application.getHeadsetBean().setVolume(application.getHeadsetBean().getVolume() - 1);
                EventBus.getDefault().post("2");
            }
            // 输出点击的按键码
//          Log.i(TAG, sb.toString());
//          Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
        } else if (KeyEvent.ACTION_UP == keyAction) {
            Log.i("chengjie", "aaa");
        }
    }
}
