package com.mjmz.lrx.sample_mjmz;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mjmz.lrx.sample_mjmz.base.BaseFragmentActivity;
import com.mjmz.lrx.sample_mjmz.tab.CartFragment;
import com.mjmz.lrx.sample_mjmz.tab.DesignFragment;
import com.mjmz.lrx.sample_mjmz.tab.GoodsFragment;
import com.mjmz.lrx.sample_mjmz.tab.HomeFragment;
import com.mjmz.lrx.sample_mjmz.tab.MyFragment;

import java.util.ArrayList;

public class TabMainActivity extends BaseFragmentActivity {
    //控件类
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //数据类
    private int defaltIndex = 0;//默认显示第一页
    private String tabTitles[] = {"首页","设计","购物车","商品","我的"};
    private ArrayList<Fragment> fragmentList;
    private MyFragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //找寻控件
        mTabLayout = (TabLayout) findViewById(R.id.tab_tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.tab_viewPager);

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
}
