package com.mjmz.lrx.sample_mjmz.language;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.mjmz.lrx.sample_mjmz.common.Const;
import com.mjmz.lrx.sample_mjmz.tools.GlobalToolsUtil;

import java.util.Locale;

/**
 * Created by george.yang on 2016-4-27.
 */
public class StringUtil {

    public static final int CHINESE = 0x000000;//中文
    public static final int ENGLISH = 0x000001;//英文

    public static int string2int (String str) {
        return string2int(str,0);
    }
    public static int string2int (String str,int def) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
        }
        return def;
    }

    /**
     * 切换语言
     */
    public static void switchLanguage(Context context,int language) {

        Configuration config = context.getResources().getConfiguration();// 获得设置对象
        Resources resources = context.getResources();// 获得res资源对象

        Locale locale = config.locale;
        switch (language) {
            case CHINESE:
                locale = Locale.CHINESE;
                break;
            case ENGLISH:
                locale = Locale.ENGLISH;
                break;
        }

        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.locale = locale; // 简体中文
        resources.updateConfiguration(config, dm);

        SharedPreferences.Editor editor = GlobalToolsUtil.getSharedPreferences(context).edit();
        editor.putInt(Const.SP.SP_LANGUAGE,language);
        editor.commit();
    }
}
