package com.mjmz.lrx.sample_mjmz;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjmz.lrx.sample_mjmz.base.BaseFragmentActivity;
import com.mjmz.lrx.sample_mjmz.customeview.ScrollIsViewPager;
import com.mjmz.lrx.sample_mjmz.tab.CartFragment;
import com.mjmz.lrx.sample_mjmz.tab.DesignFragment;
import com.mjmz.lrx.sample_mjmz.tab.GoodsFragment;
import com.mjmz.lrx.sample_mjmz.tab.HomeFragment;
import com.mjmz.lrx.sample_mjmz.tab.MyFragment;
import com.mjmz.lrx.sample_mjmz.tools.PermissionUtil;
import com.yanzhenjie.permission.AndPermission;

import java.util.ArrayList;
import java.util.List;

import static com.mjmz.lrx.sample_mjmz.tools.PermissionUtil.REQUEST_CODE_PERMISSION_LOCATION;
import static com.mjmz.lrx.sample_mjmz.tools.PermissionUtil.REQUEST_CODE_SETTING;

public class TabMainActivity extends BaseFragmentActivity {
    private boolean isRequestPermission;

    //控件类
    private TabLayout mTabLayout;
    private ScrollIsViewPager mViewPager;

    //数据类
    private int defaltIndex = 0;//默认显示第一页
    private String tabTitles[] = {"首页","设计","购物车","商品","我的"};
    private ArrayList<Fragment> fragmentList;
    private MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if ((getIntent().getFlags()& Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) { //防止安装点击打开后再次点击图标重新启动程序
            //结束你的activity
            finish();
            return;
        }
        PermissionUtil.initDefaultRequstPermission(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        init();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if(!isRequestPermission) {
//            //申请默认权限
//            Log.e("yy","here");
//            PermissionUtil.initDefaultRequstPermission(this);
//            isRequestPermission = true;
//        }
//    }

    /**
     * 初始化
     */
    private void init() {
        //找寻控件
        mTabLayout = (TabLayout) findViewById(R.id.tab_tabLayout);
        mViewPager = (ScrollIsViewPager) findViewById(R.id.tab_viewPager);
        mViewPager.setCanScroll(false);//设置不可滑动

        //数据
        fragmentList = new ArrayList<>();

        //添加主页tab
        HomeFragment fragment1 = new HomeFragment();
        fragmentList.add(fragment1);

        DesignFragment fragment2 = new DesignFragment();
        fragmentList.add(fragment2);

        CartFragment fragment3 = new CartFragment();
        fragmentList.add(fragment3);

        GoodsFragment fragment4 = new GoodsFragment();
        fragmentList.add(fragment4);

        MyFragment fragment5 = new MyFragment();
        fragmentList.add(fragment5);

        //设置适配器
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0 ; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if(tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i));
            }
        }

        //添加TabLayout选中的监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View v = tab.getCustomView();
                TextView tv = (TextView) v.findViewById(R.id.main_tab_title);
                tv.setTextColor(getResources().getColor(R.color.main_tab_textColor_selected));
                ImageView img = (ImageView) v.findViewById(R.id.main_tab_image);

                mViewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v = tab.getCustomView();
                TextView tv = (TextView) v.findViewById(R.id.main_tab_title);
                tv.setTextColor(getResources().getColor(R.color.main_tab_textColor));
                ImageView img = (ImageView) v.findViewById(R.id.main_tab_image);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //默认显示第一页
        mViewPager.setCurrentItem(defaltIndex);
    }

    /**
     * 适配器
     */
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> adapterList;

        public MyFragmentPagerAdapter(FragmentManager manager,ArrayList<Fragment> list) {
            super(manager);
            this.adapterList = list;
        }

        @Override
        public Fragment getItem(int position) {
            if(adapterList != null) {
                return adapterList.get(position);
            }
            return null;
        }

        @Override
        public int getCount() {
            if(adapterList != null) {
                return adapterList.size();
            }
            return 0;
        }

        public View getTabView(int position) {
            View v = LayoutInflater.from(TabMainActivity.this).inflate(R.layout.main_tab_view, null);
            TextView tv = (TextView) v.findViewById(R.id.main_tab_title);
            tv.setText(tabTitles[position]);
            if(position == defaltIndex) {
                tv.setTextColor(getResources().getColor(R.color.main_tab_textColor_selected));
            }else {
                tv.setTextColor(getResources().getColor(R.color.main_tab_textColor));
            }
            ImageView img = (ImageView) v.findViewById(R.id.main_tab_image);
            return v;
        }
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        super.onFailed(requestCode, deniedPermissions);
        if(requestCode == REQUEST_CODE_PERMISSION_LOCATION) {
            failedPermission(requestCode,deniedPermissions,"我们需要定位权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！");
        }
    }

    private void failedPermission(int requestCode,List<String> deniedPermissions,String permissionTip) {
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if (AndPermission.hasAlwaysDeniedPermission(this, deniedPermissions)) {
            // 第一种：用默认的提示语。
//            AndPermission.defaultSettingDialog(this, PermissionUtil.REQUEST_CODE_SETTING).show();

             //第二种：用自定义的提示语。
             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
                     .setTitle("权限申请失败")
                     .setMessage(permissionTip)
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
                     .setPositiveButton("好，去设置")
                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingService.execute();
//            你的dialog点击了取消调用：
//            settingService.cancel();
        }
    }
}
