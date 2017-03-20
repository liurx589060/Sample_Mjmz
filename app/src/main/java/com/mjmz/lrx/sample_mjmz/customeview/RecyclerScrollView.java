package com.mjmz.lrx.sample_mjmz.customeview;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by liurunxiong on 2017/3/20.
 */

public class RecyclerScrollView extends NestedScrollView {
    private int offsetY = 0;//Y值偏移量

    private View nestedView;//嵌套的View

    /**
     * 设置Y值的偏移量
     * @param y
     */
    public void setOffsetY(int y) {
        this.offsetY = y;
    }

    public RecyclerScrollView(Context context) {
        super(context);
        setNestedViewHeight();
    }

    public RecyclerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setNestedViewHeight();
    }

    public RecyclerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setNestedViewHeight();
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        int limitY = target.getTop() - offsetY;
        if(getScrollY() < limitY) {//当scrollview滑动值小于给定的值时，给scrollview滑动
            int y = limitY - getScrollY() - dy > 0?dy:limitY - getScrollY();//取需要滑动的y值
            consumed[0] = dx;
            consumed[1] = y;
            scrollBy(0,y);
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        int limitY = target.getTop() - offsetY;
        if(getScrollY() < limitY) {//当scrollview滑动值小于给定的值时，给scrollview滑动
            fling((int) velocityY);
            return true;
        }
        return false;
    }

    /**
     * 设置嵌套的滑动view的高度
     */
    public void setNestedViewHeight() {
        this.post(new Runnable() {
            @Override
            public void run() {
                View v = getChildAt(0);
                if(v instanceof ViewGroup) {
                    int num = ((ViewGroup)v).getChildCount();
                    for (int i = 0 ; i < num ; i++) {
                        View view = ((ViewGroup)v).getChildAt(i);
                        if(view instanceof RecyclerView || view instanceof ListView) {
                            ViewGroup.LayoutParams params = view.getLayoutParams();
                            params.height = Math.min(getHeight(),getHeight() - offsetY);
                            view.setLayoutParams(params);
                            nestedView = view;
                            break;
                        }
                    }
                }
            }
        });
    }
}
