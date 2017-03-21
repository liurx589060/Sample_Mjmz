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
    private boolean isRequestPermission;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!isRequestPermission) {
            //申请默认权限
            PermissionUtil.initDefaultRequstPermission(this);
            isRequestPermission = true;
        }
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        Log.e("yy","fail--BaseFragmentActivity" + deniedPermissions.get(0));
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(this, PermissionUtil.REQUEST_CODE_SETTING).show();

            // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingService.execute();
//            你的dialog点击了取消调用：
//            settingService.cancel();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        Log.e("yy","origin---BaseFragmentActivity");
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
