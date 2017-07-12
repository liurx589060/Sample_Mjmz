package com.mjmz.lrx.sample_mjmz.my;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;

/**
 * Created by liurunxiong on 2017/7/12.
 */

public class HotPicActivity extends BaseActivity {
    private WebView mWebView;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotpic);

        init();
    }

    private void init() {
        //动态添加Webview，防止持有activity导致无法回收
        mContainer = (FrameLayout) findViewById(R.id.container);
//        mWebView = (WebView) findViewById(R.id.webview);
        mWebView = new WebView(this);
        mContainer.addView(mWebView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mWebView.loadUrl("file:///android_asset/hotHtml/index.html");
        mWebView.loadUrl("https://yun.kujiale.com/design/3FO4IHKNL8N6/show");
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(this,"android");
    }
}
