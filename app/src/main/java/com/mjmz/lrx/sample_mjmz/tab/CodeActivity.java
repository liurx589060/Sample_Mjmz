package com.mjmz.lrx.sample_mjmz.tab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

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
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_capture_copy);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void resultCode(Result result, Bitmap bitmap) {
        ToastUtil.setToast(this,result.getText());
        this.finish();
    }

    @Override
    public ViewfinderView toSetViewfinderView() {
        ViewfinderView viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        //设置上下扫描杆
        viewfinderView.setmScanRod(BitmapFactory.decodeResource(getResources(),R.drawable.code_scan_middle_line));
        return viewfinderView;
    }

    @Override
    public SurfaceView getSurfaceView() {
        return (SurfaceView) findViewById(R.id.preview_view);
    }
}
