package com.mjmz.lrx.sample_mjmz.design;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.imagewrapper.ImageWrapper;
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

public class DesignInfoActivity extends BaseActivity {
    //控件类
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private RichText mRichText;

    //数据类
    private static final String IMAGE1 = "<h1>RichText</h1><p>Android平台下的富文本解析器</p><img title=\"\" src=\"http://image.tianjimedia.com/uploadImages/2015/129/56/J63MI042Z4P8.jpg\"  style=\"cursor: pointer;\"><br><br>" +
            "<h3>点击菜单查看更多Demo</h3><img src=\"http://ww2.sinaimg.cn/bmiddle/813a1fc7jw1ee4xpejq4lj20g00o0gnu.jpg\" /><p><a href=\"http://www.baidu.com\">baidu</a>" +
            "hello asdkjfgsduk <a href=\"http://www.jd.com\">jd</a></p>";
    private MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designinfo);

        //初始化
        init();
    }

    private void init() {
        //创建数据

        //找寻控件
        mTextView = (TextView) findViewById(R.id.designinfo_content_textView);
        mRecyclerView = (RecyclerView) findViewById(R.id.designinfo_recyclerView);

        //设置数据和监听
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new MyRecyclerViewAdapter(Datas.getImagesUrlArray());
        mRecyclerView.setAdapter(mAdapter);

        mRichText = RichText.from(IMAGE1);
        mRichText.imageClick(new OnImageClickListener() {
            @Override
            public void imageClicked(List<String> imageUrls, int position) {
                ToastUtil.setToast(getApplicationContext(),position + "-->>" + imageUrls.get(position));
            }
        });
        mRichText.into(mTextView);

    }

    /**
     * Adapter
     */
    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private ArrayList<String> adapterList;

        public MyRecyclerViewAdapter(ArrayList<String> list) {
            this.adapterList = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyclerview_item,parent,false);
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
            private TextView mTitle;
            private TextView mSubTitle;

            public MyViewHolder(View itemView) {
                super(itemView);
                mImageView = (ImageView) itemView.findViewById(R.id.cart_recyclerView_item_image);
                mTitle = (TextView) itemView.findViewById(R.id.cart_recyclerView_item_title);
                mSubTitle = (TextView) itemView.findViewById(R.id.cart_recyclerView_item_subTitle);

            }

            public void bindVH(int position) {
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(adapterList.get(position)).setImageView(mImageView);
                mTitle.setText("商品--" + position);
                mSubTitle.setText("地址--" + position);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRichText.clear();
        mRichText = null;
    }
}
