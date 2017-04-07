package com.mjmz.lrx.sample_mjmz.language;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by george.yang on 2016-4-27.
 */
public class ViewUtil {
    public static void updateViewLanguage(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                updateViewLanguage(vg.getChildAt(i));
            }
        } else if (view instanceof LanguageChangableView) {
            LanguageChangableView tv = (LanguageChangableView) view;
            tv.reLoadLanguage();
        }
    }
}
