package com.mjmz.lrx.sample_mjmz.tab;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lrx.imagewrapper.ImageWrapper;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.common.OnRecyclerItemClickListener;

import java.util.List;


/**
 * Created by liurunxiong on 2017/3/13.
 */

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {
    private final int TYPE_BANNER = 0x0001;//轮播
    private final int TYPE_HOT_DESIGN = 0x0002;//热门设计
    private final int TYPE_RECOM_GOODS = 0x0003;//商品推荐
    private final int TYPE_RECOM_EDITOR = 0x004;//编辑推荐

    private View mBannerView;
    private View mHotDesignView;
    private View mRecomGoodsView;

    private OnRecyclerItemClickListener listener;

    private List<String> mAdapterList;

    public void setOnRecyclerItemClickListener (OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public void setmBannerView(View view) {
        this.mBannerView = view;
    }

    public View getmBannerView() {
        return mBannerView;
    }

    public void setmHotDesignView(View view) {
        this.mHotDesignView = view;
    }

    public View getmHotDesignView() {
        return mHotDesignView;
    }

    public void setmRecomGoodsView(View view) {
        this.mRecomGoodsView = view;
    }

    public View getmRecomGoodsView() {
        return mRecomGoodsView;
    }

    public HomeRecyclerViewAdapter(List<String> list) {
        this.mAdapterList = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {return  TYPE_BANNER;}
        if(position == 1) {return  TYPE_HOT_DESIGN;}
        if(position == 2) {return  TYPE_RECOM_GOODS;}
        return TYPE_RECOM_EDITOR;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_BANNER:
                return new BannerViewHolder(mBannerView);

            case TYPE_HOT_DESIGN:
                return new HotDesignViewHolder(mHotDesignView);

            case TYPE_RECOM_GOODS:
                return new RecomGoodsViewHolder(mRecomGoodsView);

            case TYPE_RECOM_EDITOR:
                return new RecomEditorViewHolder(mInflater.inflate(R.layout.homepage_recom_editor_layout,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == TYPE_RECOM_EDITOR) {
            ((RecomEditorViewHolder)holder).bindRecomEditorVH(position);
        }else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return 3 + mAdapterList.size();
    }

    //轮播ViewHolder
    private class BannerViewHolder extends RecyclerView.ViewHolder {

        public BannerViewHolder(View itemView) {
            super(itemView);
        }
    }

    //热门设计ViewHolder
    private class HotDesignViewHolder extends RecyclerView.ViewHolder {

        public HotDesignViewHolder(View itemView) {
            super(itemView);
        }
    }

    //推荐ViewHolder
    private class RecomGoodsViewHolder extends RecyclerView.ViewHolder {

        public RecomGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }

    //编辑推荐ViewHolder
    private class RecomEditorViewHolder extends RecyclerView.ViewHolder {
        private ImageView m3DTypeImage;
        private ImageView mthumbsImage;
        private TextView mTitle;
        private TextView mSubTitle;
        private TextView mWatchNum;
        private TextView mLoveNum;

        public RecomEditorViewHolder(final View itemView) {
            super(itemView);
            mthumbsImage = (ImageView) itemView.findViewById(R.id.homepage_recom_editor_thumbs);
        }

        public void bindRecomEditorVH(int position) {
            ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(mAdapterList.get(position % mAdapterList.size())).setImageView(mthumbsImage);
        }
    }
}
