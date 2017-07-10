package com.mjmz.lrx.sample_mjmz.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;

/**
 * Created by liurunxiong on 2017/5/12.
 */

public class TransformWebViewActivity extends BaseActivity {
    private WebView mWebView;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transform_webview);

        init();
    }

    private void init() {
        //动态添加Webview，防止持有activity导致无法回收
        mContainer = (FrameLayout) findViewById(R.id.container);
        mWebView = new WebView(this);
        mContainer.addView(mWebView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView.loadUrl("file:///android_asset/jscall.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContainer.removeAllViews();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }
}
