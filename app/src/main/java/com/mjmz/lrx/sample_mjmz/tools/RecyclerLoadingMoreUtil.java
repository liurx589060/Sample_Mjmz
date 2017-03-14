package com.mjmz.lrx.sample_mjmz.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.endless.Endless;
import com.mjmz.lrx.sample_mjmz.R;

/**
 * Created by liurunxiong on 2017/3/14.
 */

public class RecyclerLoadingMoreUtil {
    private View loadingView;
    private TextView mTextView;
    private ProgressBar mProgressBar;

    public boolean isLoadingComplete;//是否数据加载完全

    public RecyclerLoadingMoreUtil(Context context) {
        loadingView = LayoutInflater.from(context).inflate(R.layout.common_loading_view,null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mProgressBar = (ProgressBar) loadingView.findViewById(R.id.progressBar);
        mTextView = (TextView) loadingView.findViewById(R.id.textView);
    }

    public View getLoadingView() {
        return loadingView;
    }

    /**
     * 加载状态
     */
    public void setLoadingState(Endless endless) {
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setText("加载中...");
        endless.loadMoreComplete();
    }

    /**
     * 完全加载完
     */
    public void setLoadingCompleted() {
        mProgressBar.setVisibility(View.GONE);
        mTextView.setText("已完全加载");
    }
}
