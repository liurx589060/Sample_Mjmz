package com.mjmz.lrx.sample_mjmz.tab;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lrx.imagewrapper.ImageWrapper;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.mjmz.lrx.sample_mjmz.common.Datas;
import com.mjmz.lrx.sample_mjmz.design.DesignInfoActivity;
import com.mjmz.lrx.sample_mjmz.tools.RecyclerLoadingMoreUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by liurunxiong on 2017/3/15.
 */

public class DesignGlobalFragment extends BaseFragment {
    //控件类
    private LuRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private LuRecyclerViewAdapter mAdapter;
    private RecyclerLoadingMoreUtil loadingMoreUtil;

    //数据类
    private ArrayList<String> mDataList;
    private Handler mHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_design_global,container,false);

        mHandler = new Handler();
        //初始化
        init(rootView);
        return rootView;
    }

    //初始化
    public void init(View view) {
        //创建数据
        mDataList = new ArrayList<>();

        //设置其他模块数据
        for (int i = 0 ; i < 5; i++) {
            mDataList.addAll(Datas.getImagesUrlArray());
        }

        //找寻控件
        mRecyclerView = (LuRecyclerView) view.findViewById(R.id.recyclerView);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        //设置数据和监听
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setHasFixedSize(true);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(mDataList);
        mAdapter = new LuRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), DesignInfoActivity.class);
                getActivity().startActivity(intent);
            }
        });

        //加载更多
        loadingMoreUtil = new RecyclerLoadingMoreUtil(getActivity());
        mAdapter.addFooterView(loadingMoreUtil.getLoadingView());
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDataList.addAll(Datas.getImagesUrlArray());
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete(Datas.getImagesUrlArray().size());
                    }
                },2000);
            }
        });

        //刷新
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList.clear();
                mDataList.addAll(Datas.getImagesUrlArray());
                mAdapter.notifyDataSetChanged();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * adapter
     */
    private class MyRecyclerViewAdapter extends SlideInLeftAnimationAdapter {
        private ArrayList<String> adapterList;

        public MyRecyclerViewAdapter(ArrayList<String> list) {
            this.adapterList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_recom_editor_layout,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder,position);
            ((MyViewHolder)holder).bindVH(position);
        }

        @Override
        public int getItemCount() {
            return adapterList.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.homepage_recom_editor_thumbs);
            }

            public void bindVH(int position) {
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(adapterList.get(position)).setImageView(mImageView);
            }
        }
    }
}
