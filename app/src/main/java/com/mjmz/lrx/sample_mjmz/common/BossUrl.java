package com.mjmz.lrx.sample_mjmz.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 服务器地址
 * Created by Administrator on 2017/7/24.
 */

public class BossUrl {
    private static final int SERVER_PUBLIC_LOCAL = 0x0001;//自己电脑配置Xampp公网服务器地址
    private static final int SERVER_NATIVE_LOCAL = 0x0002;//自己电脑配置Xampp局域网服务器地址

    private static int type = SERVER_PUBLIC_LOCAL;

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
