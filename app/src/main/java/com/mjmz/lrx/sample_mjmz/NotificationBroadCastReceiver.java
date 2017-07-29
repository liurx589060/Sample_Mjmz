package com.mjmz.lrx.sample_mjmz;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mjmz.lrx.sample_mjmz.my.MyNotifyActivity;
import com.umeng.message.UTrack;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by liurunxiong on 2017/4/6.
 */

public class NotificationBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
//            Log.e("yy", "message=" + message);      //消息体
//            Log.e("yy", "custom=" + msg.custom);    //自定义消息的内容
//            Log.e("yy", "title=" + msg.title);      //通知标题
//            Log.e("yy", "text=" + msg.text);        //通知内容

            Intent intent1 = new Intent(context, MyNotifyActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskList = manager.getRunningTasks(1);
            String str = taskList.get(0).baseActivity.getClassName();
            Log.e("yy",TabMainActivity.class.getName());
            if(!str.equals(TabMainActivity.class.getName())) {
                Log.e("yy","多个");
                Intent intent2 = new Intent(context,TabMainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent[] intents = {intent2,intent1};
                context.startActivities(intents);
            }else {
                Log.e("yy","单个");
                context.startActivity(intent1);
            }

//            context.startActivities(intents);
//            context.startActivity(intent1);

            UTrack.getInstance(context.getApplicationContext()).trackMsgClick(msg);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("yy",e.toString());
        }
    }
}
