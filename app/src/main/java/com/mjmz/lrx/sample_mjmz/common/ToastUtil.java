package com.mjmz.lrx.sample_mjmz.common;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by liurunxiong on 2017/3/17.
 */

public class ToastUtil {
    /**
     * 系统提示
     * @param context
     */
    public static void setToast(Context context) {
        if(context == null) {
            return;
        }
        Toast.makeText(context, "网络未连接!", Toast.LENGTH_SHORT).show();
    }

    /**
     * 系统提示
     * @param context
     * @param str   字符
     */
    public static void setToast(Context context,String str) {
        if(context == null) {
            return;
        }
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义系统提示
     * @param context
     * @param str        提示语
     * @param gravity    显示位置
     * @param view       自定义背景view
     */
    public static void setToast(Context context, String str , int gravity,View view) {
        if(context == null) {
            return;
        }
        Toast toast;
        if(view == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        }else {
            toast = new Toast(context);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 自定义系统提示
     * @param context
     * @param gravity    显示位置
     * @param view       自定义背景view
     */
    public static void setToast(Context context, int gravity,View view) {
        setToast(context, "网络未连接!", gravity, view);
    }

    /**
     * 自定义Toast
     * @param context
     * @param str       显示内容
     * @param time      显示时长，单位为秒
     */
    public static void setToast(Context context, String str,int gravity ,double time) {
        if(time < 1 ) {
            time = 1;
        }
        ToastSimple tSimple = ToastSimple.makeText(context, str, time);
        tSimple.show(gravity);
    }

    /**
     * 自定义Toast
     * @param context
     * @param str       显示内容
     * @param time      显示时长，单位为秒
     */
    public static void setToast(Context context, String str , double time) {
        if(time <= 0 ) {
            time = 1;
        }
        ToastSimple tSimple = ToastSimple.makeText(context, str, time);
        tSimple.show();
    }

    /**
     * 自定义Toast
     * @param context
     * @param time      显示时长，单位为秒
     */
    public static void setToast(Context context, double time) {
        if(time <= 0 ) {
            time = 1;
        }
        ToastSimple tSimple = ToastSimple.makeText(context, "网络未连接", time);
        tSimple.show();
    }
}
