package com.mjmz.lrx.sample_mjmz.tools;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;
import java.util.Objects;

/**
 * Created by liurunxiong on 2017/3/21.
 */

public class PermissionUtil {
    public static final int REQUEST_CODE_PERMISSION_SD = 100;//SD卡读写权限请求标识码
    public static final int REQUEST_CODE_PERMISSION_LOCATION = 101;//定位权限请求标识码
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 102;//相机权限请求标识码

    public static final int REQUEST_CODE_PERMISSION_MULTI = 1000;//多种权限请求标识码
    public static final int REQUEST_CODE_SETTING = 300;

    /**
     * 申请权限
     * @param activity
     * @param requestCode
     * @param permission
     */
    public static void requestPermission(final AppCompatActivity activity, int requestCode, String... permission) {
        AndPermission.with(activity)
                .requestCode(requestCode)
                .permission(permission)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(activity, rationale).show();
                    }
                }).send();
    }

    /**
     * 申请权限
     * @param fragment
     * @param requestCode
     * @param permission
     */
    public static void requestPermission(final Fragment fragment, int requestCode, String... permission) {
        AndPermission.with(fragment)
                .requestCode(requestCode)
                .permission(permission)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(fragment.getActivity(), rationale).show();
                    }
                }).send();
    }

    /**
     * 申请权限
     * @param activity
     * @param requestCode
     * @param permission
     */
    public static void requestPermission(final FragmentActivity activity, int requestCode, String... permission) {
        AndPermission.with(activity)
                .requestCode(requestCode)
                .permission(permission)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                        AndPermission.rationaleDialog(activity, rationale).show();
                    }
                }).send();
    }

    /**
     * 默认开启需要申请的权限
     * @param activity
     */
    public static void initDefaultRequstPermission(FragmentActivity activity) {
        //读取SD
        requestPermission(activity,PermissionUtil.REQUEST_CODE_PERMISSION_SD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //定位
        requestPermission(activity,PermissionUtil.REQUEST_CODE_PERMISSION_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }
}
