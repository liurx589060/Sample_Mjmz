package com.mjmz.lrx.sample_mjmz.tools;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

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
        PermissionUtil.requestPermission(activity,PermissionUtil.REQUEST_CODE_PERMISSION_SD, Manifest.permission.READ_EXTERNAL_STORAGE);
        //定位
        PermissionUtil.requestPermission(activity,PermissionUtil.REQUEST_CODE_PERMISSION_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

//    /**
//     *
//     * @param requestCode
//     * @param deniedPermissions
//     */
//    public static void permissionFail(int requestCode, List<String> deniedPermissions) {
//        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
//            // 第一种：用默认的提示语。
//            AndPermission.defaultSettingDialog(this, PermissionUtil.REQUEST_CODE_SETTING).show();
//
//            // 第二种：用自定义的提示语。
////             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
////                     .setTitle("权限申请失败")
////                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
////                     .setPositiveButton("好，去设置")
////                     .show();
//
////            第三种：自定义dialog样式。
////            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
////            你的dialog点击了确定调用：
////            settingService.execute();
////            你的dialog点击了取消调用：
////            settingService.cancel();
//        }
//    }
}
