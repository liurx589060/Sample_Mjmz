package com.mjmz.lrx.sample_mjmz.tab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;

import com.example.zxingbar.camera.MipcaActivityCapture;
import com.example.zxingbar.view.ViewfinderView;
import com.google.zxing.Result;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;

/**
 * 继承MipcaActivityCapture 可实现自己定制的扫描界面
 * Created by liurunxiong on 2017/3/20.
 */

public class CodeActivity extends MipcaActivityCapture {

    @Override
    public View getTopView() {
        View view = LayoutInflater.from(this).inflate(R.layout.code_top_view,null);
        return view;
    }

    @Override
    public View getRootView() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_code_root,null);
        return view;
    }

    @Override
    public void resultCode(Result result, Bitmap bitmap) {
        ToastUtil.setToast(this,result.getText());
        this.finish();
    }

    @Override
    public ViewfinderView toSetViewfinderView(View rootView) {
        ViewfinderView viewfinderView = (ViewfinderView) rootView.findViewById(R.id.viewfinder_view);
        //设置上下扫描杆
        viewfinderView.setmScanRod(BitmapFactory.decodeResource(getResources(),R.drawable.code_scan_middle_line));
        return viewfinderView;
    }

    @Override
    public SurfaceView getSurfaceView(View rootView) {
        return (SurfaceView) rootView.findViewById(R.id.preview_view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置外部四周阴影颜色
        setOutShadowColor(Color.parseColor("#338b008b"));
        ImageButton btn = (ImageButton) findViewById(R.id.code_scan_back);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeActivity.this.finish();
            }
        });
    }
}
