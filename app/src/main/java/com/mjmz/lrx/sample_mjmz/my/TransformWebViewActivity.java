package com.mjmz.lrx.sample_mjmz.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
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
    private Button mNoneParamsBtn;
    private Button mHasParamsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transform_webview);

        init();
    }

    private void init() {
        mNoneParamsBtn = (Button) findViewById(R.id.none_paramsBtn);
        mHasParamsBtn = (Button) findViewById(R.id.has_paramsBtn);

        //动态添加Webview，防止持有activity导致无法回收
        mContainer = (FrameLayout) findViewById(R.id.container);
//        mWebView = (WebView) findViewById(R.id.webview);
        mWebView = new WebView(this);
        mContainer.addView(mWebView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mWebView.loadUrl("file:///android_asset/jscall.html");
//        mWebView.loadUrl("file:///android_asset/marquee.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(TransformWebViewActivity.this,"android");

        mNoneParamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:javacalljs()");
            }
        });

        mHasParamsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:javacalljswith(" + "'http://blog.csdn.net/Leejizhou'" + ")");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContainer.removeAllViews();
        mWebView.removeAllViews();
        mWebView.destroy();
        mWebView = null;
    }

    @JavascriptInterface
    public void startFunction() {
        Log.e("yy","here");
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                ToastUtil.setToast(TransformWebViewActivity.this,"startFunction");
//            }
//        });

        ToastUtil.setToast(TransformWebViewActivity.this,"startFunction");
    }

    @JavascriptInterface
    public void startFunction(String str) {
        new AlertDialog.Builder(this).setTitle("startFunction").setMessage(str).show();
    }
}
