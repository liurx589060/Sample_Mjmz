package com.mjmz.lrx.sample_mjmz;

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
import java.util.ArrayList;

public class TabMainActivity extends BaseFragmentActivity {
    //控件类
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //数据类
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
        Fragment fragment1 = new Fragment();
        fragmentList.add(fragment1);

        Fragment fragment2 = new Fragment();
        fragmentList.add(fragment2);

        Fragment fragment3 = new Fragment();
        fragmentList.add(fragment3);

        Fragment fragment4 = new Fragment();
        fragmentList.add(fragment4);

        Fragment fragment5 = new Fragment();
        fragmentList.add(fragment5);

        //设置适配器
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0 ; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if(tab != null) {
                tab.setCustomView(pagerAdapter.getTabView(i)).setIcon(R.mipmap.ic_launcher);
            }
        }
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
            ImageView img = (ImageView) v.findViewById(R.id.main_tab_image);
            img.setImageResource(R.mipmap.ic_launcher);
            return v;
        }
    }
}
