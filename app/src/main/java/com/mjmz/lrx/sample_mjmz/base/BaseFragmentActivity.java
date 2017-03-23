package com.mjmz.lrx.sample_mjmz.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mjmz.lrx.sample_mjmz.tools.PermissionUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class BaseFragmentActivity extends FragmentActivity implements PermissionListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * 转给AndPermission分析结果。
         *
         * @param requestCode  请求码。
         * @param permissions  权限数组，一个或者多个。
         * @param grantResults 请求结果。
         * @param listener PermissionListener 对象。
         */
        AndPermission.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
