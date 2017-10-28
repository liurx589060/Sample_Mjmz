package com.mjmz.lrx.sample_mjmz.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/28.
 */

public class PointDataImp implements IPointData {
    private static class PointDataImpHolder {
        private static PointDataImp INSTANCE = new PointDataImp();
    }


    public static PointDataImp getInstance() {
        return PointDataImpHolder.INSTANCE;
    }

    @Override
    public void pointOnInit(Activity activity) {
//        AppsFlyerConversionListener listener = new AppsFlyerConversionListener() {
//            @Override
//            public void onInstallConversionDataLoaded(Map<String, String> map) {
//
//            }
//
//            @Override
//            public void onInstallConversionFailure(String s) {
//
//            }
//
//            @Override
//            public void onAppOpenAttribution(Map<String, String> map) {
//
//            }
//
//            @Override
//            public void onAttributionFailure(String s) {
//
//            }
//        };
//        AppsFlyerLib.getInstance().init(Const.APPSFLYER_DEV_KEY,listener);
        AppsFlyerLib.getInstance().setImeiData(getIMEI(activity));
        AppsFlyerLib.getInstance().setAndroidIdData(getAndroidId(activity));
//        AppsFlyerLib.getInstance().startTracking(activity.getApplication());
        AppsFlyerLib.getInstance().startTracking(activity.getApplication(),Const.APPSFLYER_DEV_KEY);
    }

    @Override
    public void pointOnStart(Activity activity,long currentTime) {
        Log.e("yy","打点--pointOnStart");
        Map<String,Object> map = new HashMap<>();
        map.put("time",String.valueOf(currentTime));
        AppsFlyerLib.getInstance().trackEvent(activity.getApplicationContext(),"start",map);
    }

    @Override
    public void pointOnDesignInfo(Activity activity,String roleName, String roleLevel) {
        Log.e("yy","打点--pointOnDesignInfo");
        Map<String,Object> map = new HashMap<>();
        map.put("roleName",roleName);
        map.put("roleLevel",roleLevel);
        AppsFlyerLib.getInstance().trackEvent(activity.getApplicationContext(),"designInfo",map);
    }

    @Override
    public void pointOnPay(Activity activity,String roleName, String roleLevel, long currentTime, float money) {
        Log.e("yy","打点--pointOnPay");
        Map<String,Object> map = new HashMap<>();
        map.put("roleName",roleName);
        map.put("roleLevel",roleLevel);
        map.put(AFInAppEventParameterName.REVENUE,money);
        map.put(AFInAppEventParameterName.CURRENCY,"USD");
        AppsFlyerLib.getInstance().trackEvent(activity.getApplicationContext(),"pay",map);
    }

    private String getAndroidId(Context context) {
        String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        Log.e("yy","androidId=" + androidId);
        return androidId;
    }

    private String getIMEI(Context context) {
        return "";
    }
}
