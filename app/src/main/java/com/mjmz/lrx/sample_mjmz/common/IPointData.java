package com.mjmz.lrx.sample_mjmz.common;

import android.app.Activity;
import android.content.Context;

/**
 * 打点
 * Created by Administrator on 2017/10/28.
 */

public interface IPointData {
    public void pointOnInit(Activity activity);
    public void pointOnStart(Activity activity,long currentTime);
    public void pointOnDesignInfo(Activity activity,String roleName,String roleLevel);
    public void pointOnPay(Activity activity,String roleName,String roleLevel,long currentTime,float money);
}
