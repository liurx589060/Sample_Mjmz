package com.mjmz.lrx.sample_mjmz.tab;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.imagewrapper.ImageWrapper;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;;
import com.mjmz.lrx.sample_mjmz.tools.RecyclerLoadingMoreUtil;
import com.mjmz.lrx.sample_mjmz.tools.SystemUtil;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class HomeFragment extends BaseFragment {
    /*控件*/
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LuRecyclerView mRecyclerView;
    private RecyclerLoadingMoreUtil loadingMoreUtil;

      //轮播
    private XBanner mXBanner;

      //热门设计
    private RecyclerView mHotDesignRecycler;
    private HotRecyclerViewAdapter mHotDesignRecyclerAdapter;

      //商品推荐
    private RecyclerView mRecomGoodsRecycler;
    private RecomGoodsRecyclerViewAdapter mRecomGoodsRecyclerAdapter;

    /*数据类*/
    private HomeRecyclerViewAdapter mBaseAdapter;
    private LuRecyclerViewAdapter mAdapter;
    private ArrayList<String> mDataList;
    private List<String> imgesUrl;
    private Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home,null);

        init(rootView);
        mHandler = new Handler();
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
        //创建集合
        mDataList = new ArrayList<>();

        //设置其他模块数据
        imgesUrl = new ArrayList<>();
        imgesUrl.add("http://img3.fengniao.com/forum/attachpics/913/114/36502745.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/99381473502384338.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/77991473496077677.jpg");
        imgesUrl.add("http://imageprocess.yitos.net/images/public/20160906/1291473163104906.jpg");
        mDataList.addAll(imgesUrl);

        //找寻控件
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (LuRecyclerView) view.findViewById(R.id.recyclerView);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mBaseAdapter = new HomeRecyclerViewAdapter(mDataList);
        mBaseAdapter.setmBannerView(LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.homepage_banner_layout,null));
        mBaseAdapter.setmHotDesignView(LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.homepage_hot_design_layout,null));
        mBaseAdapter.setmRecomGoodsView(LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.homepage_recom_goods_layout,null));
        mAdapter = new LuRecyclerViewAdapter(mBaseAdapter);
        mRecyclerView.setAdapter(mAdapter);

        //加载更多
        loadingMoreUtil = new RecyclerLoadingMoreUtil(getContext());
        mAdapter.addFooterView(loadingMoreUtil.getLoadingView());
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("yy","size=" + imgesUrl.size());
                if(mDataList.size() > 40) {//没数据了
                    loadingMoreUtil.setLoadingCompleted();
                    loadingMoreUtil.isLoadingComplete = true;
                    mRecyclerView.setNoMore(true);
                }else {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mRecyclerView.refreshComplete(mDataList.size());
                            mDataList.addAll(mDataList);
                            mAdapter.notifyDataSetChanged();
                        }
                    },2000);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingMoreUtil.setLoadingState();
                loadingMoreUtil.isLoadingComplete = false;
                mDataList.clear();
                mDataList.addAll(imgesUrl);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                mRecyclerView.setNoMore(false);
            }
        });

        initBannerData();
        initHotDesignData();
        initRecomGoodsData();
    }

    /**
     * 设置Banner
     */
    private void initBannerData() {
        mXBanner = (XBanner) mBaseAdapter.getmBannerView().findViewById(R.id.homepage_banner_Xbanner);
        mXBanner.setData(imgesUrl,null);
        mXBanner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                ImageWrapper.getInstance().with(getContext()).setUrl(imgesUrl.get(position)).setImageView((ImageView)view);
            }
        });
        mXBanner.setPageTransformer(Transformer.Zoom);
    }

    /**
     * 设置热门设计
     */
    private void initHotDesignData() {
        mHotDesignRecycler = (RecyclerView) mBaseAdapter.getmHotDesignView().findViewById(R.id.homepage_hot_design_recyclerView);
        mHotDesignRecycler.setLayoutManager(new LinearLayoutManager(mHotDesignRecycler.getContext(),LinearLayoutManager.HORIZONTAL,false));//水平
        mHotDesignRecycler.setHasFixedSize(true);
        mHotDesignRecyclerAdapter = new HotRecyclerViewAdapter();
        mHotDesignRecycler.setAdapter(mHotDesignRecyclerAdapter);
        mHotDesignRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildAdapterPosition(view) != 0) {
                    outRect.left = SystemUtil.dip2px(getContext(),9);
                }
            }
        });
    }

    /**
     * 设置商品推荐
     */
    private void initRecomGoodsData() {
        mRecomGoodsRecycler = (RecyclerView) mBaseAdapter.getmRecomGoodsView().findViewById(R.id.homepage_recom_goods_recyclerView);
        mRecomGoodsRecycler.setLayoutManager(new LinearLayoutManager(mHotDesignRecycler.getContext(),LinearLayoutManager.HORIZONTAL,false));//水平
        mRecomGoodsRecycler.setHasFixedSize(true);
        mRecomGoodsRecyclerAdapter = new RecomGoodsRecyclerViewAdapter();
        mRecomGoodsRecycler.setAdapter(mRecomGoodsRecyclerAdapter);
        mRecomGoodsRecycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if(parent.getChildAdapterPosition(view) != 0) {
                    outRect.left = SystemUtil.dip2px(getContext(),9);
                }
            }
        });
    }

    /**
     * 热门设计的adapter
     */
    private class HotRecyclerViewAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_hot_design_recyclerview_item,parent,false);
            return new HotDesignViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((HotDesignViewHolder)holder).bindVH(position);
        }

        @Override
        public int getItemCount() {
            return 15;
        }

        private class HotDesignViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;

            public HotDesignViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.hot_design_recyclerview_item_image);
            }

            public void bindVH(int position) {
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(imgesUrl.get(position % imgesUrl.size())).setImageView(mImageView);
            }
        }
    }

    /**
     * 商品推荐的adapter
     */
    private class RecomGoodsRecyclerViewAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_recom_goods_recyclerview_item,parent,false);
            return new RecomGoodsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((RecomGoodsViewHolder)holder).bindVH(position);
        }

        @Override
        public int getItemCount() {
            return 40;
        }

        private class RecomGoodsViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;
            private TextView mTitle;

            public RecomGoodsViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.recom_goods_recyclerView_item_image);
                mTitle = (TextView) itemView.findViewById(R.id.recom_goods_recyclerView_item_title);

            }

            public void bindVH(int position) {
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(imgesUrl.get(position % imgesUrl.size())).setImageView(mImageView);
                mTitle.setText("推荐商品-" + position);
            }
        }
    }
}
