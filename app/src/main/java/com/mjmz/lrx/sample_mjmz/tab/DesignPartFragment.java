package com.mjmz.lrx.sample_mjmz.tab;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.imagewrapper.ImageLoadedListener;
import com.example.imagewrapper.ImageWrapper;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.mjmz.lrx.sample_mjmz.tools.RecyclerLoadingMoreUtil;
import com.mjmz.lrx.sample_mjmz.tools.SystemUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by liurunxiong on 2017/3/15.
 */

public class DesignPartFragment extends BaseFragment {

    //控件类
    private LuRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private LuRecyclerViewAdapter mAdapter;
    private RecyclerLoadingMoreUtil loadingMoreUtil;

    //数据类
    private List<String> imgesUrl;
    private ArrayList<String> mDataList;
    private Handler mHandler;

    private ArrayList<Integer> heightList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_design_part,container,false);

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
        imgesUrl = new ArrayList<>();
        heightList = new ArrayList<>();
        imgesUrl.add("http://img1.cache.netease.com/catchpic/6/6B/6B501A4E51E2E5177DC8BD4F97702FE2.jpg");
        imgesUrl.add("http://img2.cache.netease.com/sports/2008/9/4/20080904085704fdd3a.jpg");
        imgesUrl.add("http://a0.att.hudong.com/17/52/01300000432220131367520438422.jpg");
        imgesUrl.add("http://imglf1.ph.126.net/tU0icGyQKWUd6YCpvE2G5g==/6608503588073788924.jpg");
        for (int i = 0 ; i < 30 ; i ++) {
            mDataList.addAll(imgesUrl);
        }
        getRedomHeight(mDataList);

        //找寻控件
        mRecyclerView = (LuRecyclerView) view.findViewById(R.id.recyclerView);
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        //设置数据和监听
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(mDataList);
        mAdapter = new LuRecyclerViewAdapter(adapter);
        //先要setAdapter在添加底视图
        mRecyclerView.setAdapter(mAdapter);
        //底加载视图
        loadingMoreUtil = new RecyclerLoadingMoreUtil(getActivity());
        mAdapter.addFooterView(loadingMoreUtil.getLoadingView());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int space = SystemUtil.dip2px(getContext(),6);
                outRect.left=space;
                outRect.right=0;
                outRect.bottom=0;
                outRect.top = space;
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }
        });

        //加载更多
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDataList.addAll(imgesUrl);
                        getRedomHeight(imgesUrl);
                        mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
                        mRecyclerView.refreshComplete(imgesUrl.size());
                    }
                },2000);
            }
        });

        //刷新
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList.clear();
                heightList.clear();
                mDataList.addAll(imgesUrl);
                getRedomHeight(mDataList);
                mAdapter.notifyDataSetChanged();
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }

    private void getRedomHeight(List l) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0 ; i <l.size() ; i ++) {
            list.add((int)(200+Math.random()*400));
        }
        heightList.addAll(list);
    }

    /**
     * adapter
     */
    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private ArrayList<String> adapterList;

        public MyRecyclerViewAdapter(ArrayList<String> list) {
            this.adapterList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_design_part_recyclerview_item,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
                mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            }

            public void bindVH(final int position) {
                ImageWrapper.getInstance().with(this.itemView.getContext()).downImage(adapterList.get(position), new ImageLoadedListener() {
                    @Override
                    public void imageLoaded(Bitmap bitmap, String url) {
                        Log.e("yy",position + "-->>" + "with=" + bitmap.getWidth() + "--height=" + bitmap.getHeight());
                        float rate = bitmap.getHeight()*1.0f / bitmap.getWidth()*1.0f;
                        ViewGroup.LayoutParams params =  mImageView.getLayoutParams();//得到item的LayoutParams布局参数
                        params.height = (int) (mImageView.getWidth() * rate);//把随机的高度赋予itemView布局
                        Log.e("zz","height--" + params.height + "[][][]width=" + mImageView.getWidth());
                        mImageView.setLayoutParams(params);//把params设置给itemView布局
                        ImageWrapper.getInstance().with(itemView.getContext()).setUrl(adapterList.get(position)).setImageView(mImageView);
                    }
                });

//                ViewGroup.LayoutParams params =  this.mImageView.getLayoutParams();//得到item的LayoutParams布局参数
//                params.height = heightList.get(position);//把随机的高度赋予itemView布局
//                this.mImageView.setLayoutParams(params);//把params设置给itemView布局
//                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(adapterList.get(position)).setImageView(mImageView);
            }
        }
    }
}
