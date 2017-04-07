package com.mjmz.lrx.sample_mjmz.tools;

import android.content.Context;
import android.content.SharedPreferences;
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
}
