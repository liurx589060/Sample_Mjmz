package com.mjmz.lrx.sample_mjmz.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.common.Const;

/**
 * Created by liurunxiong on 2017/3/14.
 */

public class GlobalToolsUtil {
    /**
     * 获取SharedPreferences
     * @param context
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Const.SP_NAME,Context.MODE_PRIVATE);
        return sp;
    }

    /**
     * 检测网络是否连接
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null&& info.isConnected()) {
                    // 判断当前网络是否已经连接
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}
