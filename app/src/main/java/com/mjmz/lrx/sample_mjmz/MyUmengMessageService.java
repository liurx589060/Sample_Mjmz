package com.mjmz.lrx.sample_mjmz;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/**
 * Created by liurunxiong on 2017/4/6.
 */

public class MyUmengMessageService extends UmengMessageService {

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));

            Notification notification = getNotification(msg,message);
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Notification getNotification(UMessage msg,String message) {
        Intent intent = new Intent(this,NotificationBroadCastReceiver.class);
        intent.putExtra(AgooConstants.MESSAGE_BODY,message);
        PendingIntent contentIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //自定义通知样式
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//        RemoteViews myNotificationView = new RemoteViews(this.getPackageName(),
//                R.layout.notification_view);
//        myNotificationView.setTextViewText(R.id.notification_title, msg.title);
//        myNotificationView.setTextViewText(R.id.notification_text, msg.text);
//        myNotificationView.setImageViewResource(R.id.notification_large_icon,
//                R.mipmap.ic_launcher);
//        myNotificationView.setImageViewResource(R.id.notification_small_icon,
//                R.mipmap.ic_launcher);
//        builder.setContent(myNotificationView)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker(msg.ticker)
//                .setAutoCancel(true)
//                .setContentIntent(contentIntent);


        //默认的通知样式
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(msg.title)
                .setContentText(msg.text)
                .setContentIntent(contentIntent)
                .setTicker(msg.ticker)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
				.setAutoCancel(true)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.icon_sample_mjmz)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon_sample_mjmz));

        // 指定内容意图
        Notification notification = builder.build();
        return notification;
    }

//    @Override
//    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
////        return super.onStartCommand(intent, flags, startId);
//        return START_STICKY;
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Intent intent = new Intent(this,MyUmengMessageService.class);
//        startService(intent);
//    }
}
