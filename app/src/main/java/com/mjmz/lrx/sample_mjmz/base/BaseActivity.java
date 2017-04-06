package com.mjmz.lrx.sample_mjmz.base;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mjmz.lrx.sample_mjmz.tools.PermissionUtil;
import com.umeng.message.PushAgent;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import java.util.List;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class BaseActivity extends AppCompatActivity implements PermissionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
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
