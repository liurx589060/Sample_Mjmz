package com.mjmz.lrx.sample_mjmz.customeview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Created by daven.liu on 2018/1/10 0010.
 */

public class FloatLayoutView extends LinearLayout {
    public FloatLayoutView(Context context) {
        super(context);
    }

    public FloatLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("yy","dispatchKeyEvent=" + event.getKeyCode() + "--result=" + super.dispatchKeyEvent(event));
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.e("yy","onKeyUp=" + keyCode);
        return super.onKeyUp(keyCode, event);
    }
}
