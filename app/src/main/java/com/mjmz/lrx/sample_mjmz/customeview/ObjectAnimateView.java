package com.mjmz.lrx.sample_mjmz.customeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.TypeEvaluator;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by liurunxiong on 2017/6/29.
 */

public class ObjectAnimateView extends View {
    public static final float RADIUS = 50f;

    private Point currentPoint;

    private Paint mPaint;

    private String color;

//    public String getColor() {
//        return color;
//    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public ObjectAnimateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    private void startAnimation() {
        final Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.setRepeatCount(ValueAnimator.INFINITE);
        final ColorEvaluator evaluator = new ColorEvaluator();
        ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color", evaluator,
                "#0000FF", "#FF0000");
        anim2.setRepeatCount(ObjectAnimator.INFINITE);
        anim2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                evaluator.mCurrentRed = -1;
                evaluator.mCurrentGreen = -1;
                evaluator.mCurrentBlue = -1;
            }
        });
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(anim).with(anim2);
        animSet.setDuration(5000);
        animSet.start();
    }

    public class Point {

        private float x;

        private float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }

    public class PointEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            Point startPoint = (Point) startValue;
            Point endPoint = (Point) endValue;
            float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
            float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
            Point point = new Point(x, y);
            return point;
        }
    }

    public class ColorEvaluator implements TypeEvaluator {

        public int mCurrentRed = -1;

        public int mCurrentGreen = -1;

        public int mCurrentBlue = -1;

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            String startColor = (String) startValue;
            String endColor = (String) endValue;
            int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
            int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
            int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
            int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
            int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
            int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);
            // 初始化颜色的值
            if (mCurrentRed == -1) {
                mCurrentRed = startRed;
            }
            if (mCurrentGreen == -1) {
                mCurrentGreen = startGreen;
            }
            if (mCurrentBlue == -1) {
                mCurrentBlue = startBlue;
            }
            // 计算初始颜色和结束颜色之间的差值
            int redDiff = Math.abs(startRed - endRed);
            int greenDiff = Math.abs(startGreen - endGreen);
            int blueDiff = Math.abs(startBlue - endBlue);
            int colorDiff = redDiff + greenDiff + blueDiff;
            if (mCurrentRed != endRed) {
                mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0,
                        fraction);
            } else if (mCurrentGreen != endGreen) {
                mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff,
                        redDiff, fraction);
            } else if (mCurrentBlue != endBlue) {
                mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                        redDiff + greenDiff, fraction);
            }

            // 将计算出的当前颜色的值组装返回
            String currentColor = "#" + getHexString(mCurrentRed)
                    + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
            return currentColor;
        }

        /**
         * 根据fraction值来计算当前的颜色。
         */
        private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                    int offset, float fraction) {
            int currentColor;
            if (startColor > endColor) {
                currentColor = (int) (startColor - (fraction * colorDiff - offset));
                if (currentColor < endColor) {
                    currentColor = endColor;
                }
            } else {
                currentColor = (int) (startColor + (fraction * colorDiff - offset));
                if (currentColor > endColor) {
                    currentColor = endColor;
                }
            }
            return currentColor;
        }

        /**
         * 将10进制颜色值转换成16进制。
         */
        private String getHexString(int value) {
            String hexString = Integer.toHexString(value);
            if (hexString.length() == 1) {
                hexString = "0" + hexString;
            }
            return hexString;
        }

    }
}
