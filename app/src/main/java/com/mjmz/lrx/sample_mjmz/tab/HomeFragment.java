package com.mjmz.lrx.sample_mjmz.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.XBannerViewPager;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class HomeFragment extends BaseFragment {
    /*控件*/
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

      //轮播
    private XBanner mXBanner;

      //热门设计
    private RecyclerView mHotDesignRecycler;

      //商品推荐
    private RecyclerView mRecomGoodsRecycler;

    /*数据类*/
    private HomeRecyclerViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home,null);

        init(rootView);
        return rootView;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mXBanner != null) {
            mXBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mXBanner != null) {
            mXBanner.stopAutoPlay();
        }
    }

    /**
     * 初始化
     */
    private void init(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mAdapter = new HomeRecyclerViewAdapter();
        mAdapter.setmBannerView(LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.homepage_banner_layout,null));
        mAdapter.setmHotDesignView(LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.homepage_hot_design_layout,null));
        mAdapter.setmRecomGoodsView(LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.homepage_recom_goods_layout,null));
        mRecyclerView.setAdapter(mAdapter);

        //设置其他模块数据
        initBannerData(mAdapter.getmBannerView());
    }

    /**
     * 设置Banner
     */
    private void initBannerData(View view) {
        if(view != null) {
            mXBanner = (XBanner) mAdapter.getmBannerView().findViewById(R.id.homepage_banner_Xbanner);
            final List<String> imgesUrl = new ArrayList<>();
            imgesUrl.add("http://img3.fengniao.com/forum/attachpics/913/114/36502745.jpg");
            imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/99381473502384338.jpg");
            imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/77991473496077677.jpg");
            imgesUrl.add("http://imageprocess.yitos.net/images/public/20160906/1291473163104906.jpg");
            mXBanner.setData(imgesUrl,null);
            mXBanner.setmAdapter(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    Glide.with(banner.getContext()).load(imgesUrl.get(position)).into((ImageView)view);
                }
            });
            mXBanner.setPageTransformer(Transformer.Zoom);
        }
    }

    /**
     * 设置热门设计
     */
    private void initHotDesignData(View view) {

    }

    /**
     * 设置商品推荐
     */
    private void initRecomGoodsData(View view) {

    }
}
