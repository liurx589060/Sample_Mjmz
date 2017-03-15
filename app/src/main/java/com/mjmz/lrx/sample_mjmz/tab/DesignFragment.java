package com.mjmz.lrx.sample_mjmz.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.mjmz.lrx.sample_mjmz.customeview.ScrollIsViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class DesignFragment extends BaseFragment implements View.OnClickListener{
    //控件类
    private ScrollIsViewPager mViewPager;
    private RadioButton mGlobalRadio;
    private RadioButton mPartRadio;

    //数据类
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_design,null);
        //初始化
        init(rootView);

        return rootView;
    }

    /**
     * 初始化
     * @param view
     */
    private void init(View view) {
        //创建数据
        fragmentList = new ArrayList<>();
        DesignGlobalFragment fragment1 = new DesignGlobalFragment();
        fragmentList.add(fragment1);

        DesignPartFragment fragment2 = new DesignPartFragment();
        fragmentList.add(fragment2);

        //找寻控件
        mViewPager = (ScrollIsViewPager) view.findViewById(R.id.viewPager);
        mViewPager.setCanScroll(false);

        mGlobalRadio = (RadioButton) view.findViewById(R.id.global_radio);
        mPartRadio = (RadioButton) view.findViewById(R.id.part_radio);

        //设置数据和监听
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(),fragmentList);
        mViewPager.setAdapter(adapter);
        mGlobalRadio.setOnClickListener(this);
        mPartRadio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.global_radio://整屋设计
                mViewPager.setCurrentItem(0);
                break;

            case R.id.part_radio://局部设计
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> adapterList;

        public MyFragmentPagerAdapter(FragmentManager manager, List<Fragment> list) {
            super(manager);
            this.adapterList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return adapterList.get(position);
        }

        @Override
        public int getCount() {
            return adapterList.size();
        }
    }
}
