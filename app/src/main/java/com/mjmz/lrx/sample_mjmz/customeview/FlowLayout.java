package com.mjmz.lrx.sample_mjmz.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mjmz.lrx.sample_mjmz.tools.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */

public class FlowLayout extends ViewGroup {
    private FlowLayoutAdapter mAdapter;
    private List<View> mViewsFromAdapter;

    private final int DefaultColumnMargin = DisplayUtils.dip2px(4);//默认每个标签水平间距
    private final int DefaultLineMargin = DisplayUtils.dip2px(4);//默认每个标签竖直间距

    //存储所有的View，按行记录
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    //记录每一行的最大高度
    private List<Integer> mLineHeightList = new ArrayList<Integer>();

    private int leftMargin,rightMargin,topMargin,bottomMargin = 0;

    public FlowLayout(Context context) {
        super(context);
        init();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewsFromAdapter = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mAllViews.clear();
        mLineHeightList.clear();

        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount() == 0?mViewsFromAdapter.size():getChildCount();
        ArrayList<View> lineViews = new ArrayList<>();

        for(int i = 0 ; i < childCount ; i ++) {
            View child;
            if(getChildCount() == 0) {
                child = mViewsFromAdapter.get(i);
            }else {
                child = getChildAt(i);
            }
//            View child = getChildAt(i);
            if(child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            leftMargin = params.leftMargin == 0?DefaultColumnMargin:params.leftMargin;
            rightMargin = params.rightMargin == 0?DefaultColumnMargin:params.rightMargin;

            topMargin = params.topMargin == 0?DefaultLineMargin:params.topMargin;
            bottomMargin = params.bottomMargin == 0?DefaultLineMargin:params.bottomMargin;

            int childWidth = child.getMeasuredWidth() + leftMargin + rightMargin;
            int childHeight = child.getMeasuredHeight() + topMargin + bottomMargin;

            if(lineWidth + childWidth > sizeWidth) {//大于父控件宽度
                width = Math.max(lineWidth,childWidth);
                lineWidth = childWidth;//重新开启新一行

                mLineHeightList.add(lineHeight);
                mAllViews.add(lineViews);
                lineViews = new ArrayList<>();

                height += lineHeight;
            }else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }
            lineViews.add(child);
        }

        // 记录最后一行
        mLineHeightList.add(lineHeight);
        mAllViews.add(lineViews);

        width = Math.max(width,lineWidth);
        height += lineHeight;
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY?sizeWidth:width,
                modeHeight == MeasureSpec.EXACTLY?sizeHeight:height);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        mAllViews.clear();
//        mLineHeightList.clear();

//        int width = getWidth();
//        int lineHeight = 0;//包含了margin
//        int lineWidth = 0;
//
//        List<View> lineViews = new ArrayList<>();
//        int childCount = getChildCount();
//        Log.e("yy","childCount=" + childCount);
//        for (int i = 0 ; i < childCount ; i ++) {
//            View child = getChildAt(i);
//            if(child.getVisibility() == GONE) {
//                continue;
//            }
//            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
//
//            int leftMargin,rightMargin,topMargin,bottomMargin = 0;
//            leftMargin = params.leftMargin == 0?DefaultColumnMargin:params.leftMargin;
//            rightMargin = params.rightMargin == 0?DefaultColumnMargin:params.rightMargin;
//
//            topMargin = params.topMargin == 0?DefaultLineMargin:params.topMargin;
//            bottomMargin = params.bottomMargin == 0?DefaultLineMargin:params.bottomMargin;
//            int childWidth = child.getMeasuredWidth() + leftMargin + rightMargin;
//            int childHeight = child.getMeasuredHeight() + topMargin + bottomMargin;
//
//            if(lineWidth + childWidth > width) {//大于一行宽度
//                mLineHeightList.add(lineHeight);
//                mAllViews.add(lineViews);
//                lineWidth = 0;
//                lineViews = new ArrayList<>();
//            }
//
//            lineWidth += childWidth;
//            lineHeight = Math.max(lineHeight,childHeight);
//            lineViews.add(child);
//        }
//
//        // 记录最后一行
//        mLineHeightList.add(lineHeight);
//        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        // 得到总行数
        int lineNums = mAllViews.size();

        int lineHeight = 0;
        List<View> lineViews;

        for (int i = 0 ; i < lineNums ; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeightList.get(i);

//            Log.e("yy","****" + i + "个数=" + lineViews.size());
            for (int j = 0 ; j < lineViews.size() ; j++) {
//                Log.e("yy","行数=" + i + "-->>" + j);
                View lineChild = lineViews.get(j);
                if(lineChild.getVisibility() == GONE) {
                    continue;
                }
//                MarginLayoutParams lp = (MarginLayoutParams) lineChild.getLayoutParams();

                //计算childView的left,top,right,bottom
                int lc = left + leftMargin;
                int tc = top + topMargin;
                int rc = lineChild.getMeasuredWidth() + lc;
                int bc = lineChild.getMeasuredHeight() + tc;

                lineChild.layout(lc,tc,rc,bc);

                left += lineChild.getMeasuredWidth() + leftMargin + rightMargin;
            }

            left = 0;
            top += lineHeight;
        }
    }

    //ViewGroup能够支持margin
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(),attrs);
    }

    public void setAdapter(FlowLayoutAdapter adapter) {
        if(adapter != null) {
            this.mAdapter = adapter;
            mAdapter.setFlowLayout(this);

            mViewsFromAdapter = mAdapter.getAllViews();
        }
    }

    public FlowLayoutAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.removeAllViews();
    }

    public static abstract class FlowLayoutAdapter {
        private FlowLayout mFlowLayout;
        private List<View> list = new ArrayList<>();

        public abstract int getItemCount();
        public abstract View getView(int index,FlowLayout flowLayout);
        public void setFlowLayout(FlowLayout flowLayout) {
            this.mFlowLayout = flowLayout;
        }
        public final List<View> getAllViews() {
            list.clear();
            mFlowLayout.removeAllViews();
            for (int i = 0; i < getItemCount();i++) {
                View view = getView(i,mFlowLayout);
                list.add(getView(i,mFlowLayout));
                mFlowLayout.addView(view,new MarginLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)));
            }

            mFlowLayout.invalidate();
            return list;
        }

        public void notifyDataSetChanged() {
            getAllViews();
        }
    }
}
