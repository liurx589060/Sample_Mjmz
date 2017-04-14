package com.mjmz.lrx.sample_mjmz.goods;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lrx.imagewrapper.ImageWrapper;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.Datas;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/19.
 */

public class GoodsInfoActivity extends BaseActivity {
    //控件类
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;

    //数据类
    private ArrayList<String> mDataList;

    private static final String IMAGE1 = "<h1>RichText</h1><p>Android平台下的富文本解析器</p><img title=\"\" src=\"http://image.tianjimedia.com/uploadImages/2015/129/56/J63MI042Z4P8.jpg\"  style=\"cursor: pointer;\"><br><br>" +
            "<h3>点击菜单查看更多Demo</h3><img src=\"http://ww2.sinaimg.cn/bmiddle/813a1fc7jw1ee4xpejq4lj20g00o0gnu.jpg\" /><p><a href=\"http://www.baidu.com\">baidu</a>" +
            "hello asdkjfgsduk <a href=\"http://www.jd.com\">jd</a></p>";

    private static final String GIF_TEST = "<img src=\"http://ww4.sinaimg.cn/large/5cfc088ejw1f3jcujb6d6g20ap08mb2c.gif\">";


    private RichText mRichText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsinfo);

        //初始化
        init();
    }

    //初始化
    private void init() {
        //创建数据
        mDataList = new ArrayList<>();
        mDataList.addAll(Datas.getImagesUrlArray());

        //找寻控件类
        mRecyclerView = (RecyclerView) findViewById(R.id.goodsinfo_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new MyRecyclerViewAdapter(mDataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRichText != null) {
            mRichText.clear();
            mRichText = null;
        }
    }

    /**
     * Adapter
     */
    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private ArrayList<String> adapterList;
        private final int TYPE_RICHTEXT = 0x00001;//富文本布局
        private final int TYPE_NORMAL = 0x00002;//item

        public MyRecyclerViewAdapter(ArrayList<String> list) {
            this.adapterList = list;
            adapterList.add(0,"" + TYPE_RICHTEXT);
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0) {return TYPE_RICHTEXT;}
            return TYPE_NORMAL;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_RICHTEXT) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goodsinfo_recyclerview_item_richtext,parent,false);
                return new RichTextViewHolder(view);
            }else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyclerview_item,parent,false);
                return new MyRecyclerViewAdapter.MyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(getItemViewType(position)  == TYPE_RICHTEXT) {
                ((RichTextViewHolder)holder).bindVH(position);
            }else {
                ((MyRecyclerViewAdapter.MyViewHolder)holder).bindVH(position);
            }
        }

        @Override
        public int getItemCount() {
            return adapterList.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView mImageView;
            private TextView mTitle;
            private TextView mSubTitle;

            public MyViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.cart_recyclerView_item_image);
                mTitle = (TextView) itemView.findViewById(R.id.cart_recyclerView_item_title);
                mSubTitle = (TextView) itemView.findViewById(R.id.cart_recyclerView_item_subTitle);

            }

            public void bindVH(int position) {
                Log.e("yy","bind--" + position);
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(adapterList.get(position)).setImageView(mImageView);
                mTitle.setText("商品--" + position);
                mSubTitle.setText("地址--" + position);
            }
        }

        private class RichTextViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            public RichTextViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.richText);

            }

            public void bindVH(int position) {
                mRichText = RichText.from(IMAGE1);
                mRichText.imageClick(new OnImageClickListener() {
                    @Override
                    public void imageClicked(List<String> imageUrls, int position) {
                        ToastUtil.setToast(getApplicationContext(),position + "-->>" + imageUrls.get(position));
                    }
                });
                mRichText.into(mTextView);
            }
        }
    }
}
