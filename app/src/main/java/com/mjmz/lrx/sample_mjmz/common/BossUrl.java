package com.mjmz.lrx.sample_mjmz.common;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.mjmz.lrx.sample_mjmz.tools.GlobalToolsUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * 服务器地址
 * Created by Administrator on 2017/7/24.
 */

public class BossUrl {
    private static final int SERVER_PUBLIC_LOCAL = 0x0001;//自己电脑配置Xampp公网服务器地址
    private static final int SERVER_NATIVE_LOCAL = 0x0002;//自己电脑配置Xampp局域网服务器地址

//    private static int type = SERVER_NATIVE_LOCAL;
    private static int type = SERVER_PUBLIC_LOCAL;

    private static Application application;
    /**网络请求的公共参数*/
    private static Map<String,String> paramsMap = new HashMap();

    private static String imei;//标识码

    public static void init(Application app) {
        application = app;
        TelephonyManager TelephonyMgr = (TelephonyManager)application.getSystemService(TELEPHONY_SERVICE);
        imei = TelephonyMgr.getDeviceId();
        paramsMap.put("imei",imei);
    }

    public static Map getParamsMap(String Method) {
        SharedPreferences sp = GlobalToolsUtil.getSharedPreferences(application);
        String token = sp.getString(Const.SP.SP_TOKEN,"");
        String time = String.valueOf(System.currentTimeMillis()/1000);
        String signature = GlobalToolsUtil.md5(Method + token + time);
        paramsMap.put("time",time);
        paramsMap.put("signature",signature);
        Log.e("yy",token);
        return paramsMap;
    }

    public static String getParamsStr(String method) {
        Iterator iterator = getParamsMap(method).entrySet().iterator();
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String,String> entry = (Map.Entry<String, String>) iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            builder.append(key + "=");
            builder.append(value);
            builder.append("&");
        }
        return builder.toString();
    }

    /**
     * 获取服务器域名地址
     * @return
     */
    public static String getServiceBossUrl() {
        String url = null;
        switch (type) {
            case SERVER_PUBLIC_LOCAL:
                url = "http://1771r224f9.imwork.net/thinkphp/Sample_Mjmz/";
                break;

            case SERVER_NATIVE_LOCAL:
                url = "http://192.168.1.101/thinkphp/Sample_Mjmz/";
                break;
        }
        return url;
    }
}
