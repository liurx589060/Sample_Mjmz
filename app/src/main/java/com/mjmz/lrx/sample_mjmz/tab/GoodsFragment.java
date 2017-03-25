package com.mjmz.lrx.sample_mjmz.tab;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lrx.imagewrapper.ImageWrapper;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.mjmz.lrx.sample_mjmz.common.Datas;
import com.mjmz.lrx.sample_mjmz.goods.GoodsInfoActivity;
import com.mjmz.lrx.sample_mjmz.tools.RecyclerLoadingMoreUtil;
import com.mjmz.lrx.sample_mjmz.tools.SystemUtil;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class GoodsFragment extends BaseFragment {
    //控件类
    private SwipeRefreshLayout mSwipeRefresh;
    private LuRecyclerView mRecyclerView;
    private LuRecyclerViewAdapter mAdapter;
    private RecyclerLoadingMoreUtil loadingMoreUtil;

    //数据类
    private final int COLUMNCOUNT = 2;//显示的列表
    private ArrayList<String> firstDataList;//第一模块数据
    private ArrayList<String> secondDataList;//第二模块数据

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_goods,null);

        //初始化
        init(rootView);
        return rootView;
    }

    //初始化
    private void init(View view) {
        //创建数据
        firstDataList = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i ++) {
            firstDataList.addAll(Datas.getImagesUrlArray());
        }

        secondDataList = new ArrayList<>();
        for (int i = 0 ; i < 50; i ++) {
            secondDataList.addAll(Datas.getImagesUrlArray());
        }

        //找寻控件
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (LuRecyclerView) view.findViewById(R.id.recyclerView);

        //设置数据和监听
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),COLUMNCOUNT,GridLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(firstDataList,secondDataList);
        mAdapter = new LuRecyclerViewAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);

        //添加底部加载视图
        loadingMoreUtil = new RecyclerLoadingMoreUtil(getActivity());
        mAdapter.addFooterView(loadingMoreUtil.getLoadingView());

        //设置间隙
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if(parent.getChildLayoutPosition(view) == firstDataList.size() + 1) {
                    outRect.top = SystemUtil.dip2px(getActivity(),40);
                }else {
                    outRect.top = SystemUtil.dip2px(getActivity(),8);
                }
                outRect.left = -SystemUtil.dip2px(getActivity(),4);
                outRect.right = SystemUtil.dip2px(getActivity(),12);
                outRect.bottom = 0;
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), GoodsInfoActivity.class);
                getActivity().startActivity(intent);
                Log.e("yy","Clicked=" + position);
            }
        });
    }

    /**
     * adapter
     */
    private class MyRecyclerViewAdapter extends SlideInLeftAnimationAdapter {
        private final int TYPE_TITLE = 0x0001;//分类section
        private final int TYPE_ITEM = 0x0002;//item

        private ArrayList<Integer> typeFlagList;//分类标识

        private ArrayList<String> adapterList;

        public MyRecyclerViewAdapter(ArrayList<String> firstList,ArrayList<String> secondList) {
            adapterList = new ArrayList<>();
            typeFlagList = new ArrayList<>();

            typeFlagList.add(0);
            adapterList.addAll(firstList);

            typeFlagList.add(firstList.size() + typeFlagList.size());
            adapterList.addAll(secondList);
        }

        @Override
        public int getItemViewType(int position) {
            if(typeFlagList.contains(position)) {return TYPE_TITLE;}
            return TYPE_ITEM;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            switch (viewType) {
                case TYPE_TITLE:
                    return new TitleViewHolder(mInflater.inflate(R.layout.goods_recyclerview_section,parent,false));
                default:
                    return new ItemViewHolder(mInflater.inflate(R.layout.goods_recyclerview_item,parent,false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            super.onBindViewHolder(holder,position);
            int type = getItemViewType(position);
            if(type == TYPE_TITLE) {
                ((TitleViewHolder)holder).bindVH(position);
            }else {
                ((ItemViewHolder)holder).bindVH(position);
            }
        }

        @Override
        public int getItemCount() {
            return typeFlagList.size() + adapterList.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(typeFlagList.contains(position) || position == getItemCount()) {
                        return manager.getSpanCount();
                    }
                    return 1;
                }
            });

       }

        /**
         * item的ViewHolder
         */
        private class ItemViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;
            private TextView mTitle;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.goods_recyclerView_item_image);
                mTitle = (TextView) itemView.findViewById(R.id.goods_recyclerView_item_title);
            }

            public void bindVH(int position) {
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(adapterList.get(position % adapterList.size())).setImageView(mImageView);
                if(position < typeFlagList.get(1)) {
                    mTitle.setText("最新上架-" + (position - typeFlagList.get(0) - 1));
                }else if (position > typeFlagList.get(1)) {
                    mTitle.setText("热门商品-" + (position - typeFlagList.get(1) - 1));
                }
            }
        }

        /**
         * Title的ViewHolder
         */
        private class TitleViewHolder extends RecyclerView.ViewHolder {
            private TextView mTitle;

            public TitleViewHolder(View itemView) {
                super(itemView);
                mTitle = (TextView) itemView.findViewById(R.id.goods_recyclerView_secition_title);
            }

            public void bindVH(int position) {
                if(position == typeFlagList.get(0)) {
                    mTitle.setText("-最新上架-");
                }else if (position == typeFlagList.get(1)) {
                    mTitle.setText("-热门商品-");
                }
            }
        }
    }
}
