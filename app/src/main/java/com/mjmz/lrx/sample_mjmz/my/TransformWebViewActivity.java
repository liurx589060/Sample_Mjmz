package com.mjmz.lrx.sample_mjmz.my;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;

/**
 * Created by liurunxiong on 2017/5/12.
 */

public class TransformWebViewActivity extends BaseActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transform_webview);

        init();
    }

    private void init() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("file:///android_asset/jscall.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
    }
}
