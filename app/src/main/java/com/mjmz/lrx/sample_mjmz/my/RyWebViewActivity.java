package com.mjmz.lrx.sample_mjmz.my;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lrx.imagewrapper.ImageWrapper;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.Datas;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;
import com.mjmz.lrx.sample_mjmz.tools.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23.
 */

public class RyWebViewActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private WebView mWebView;
//    private View mWebContainView;

    private MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rywebview);

        init();
    }

    private void init() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mWebView = (WebView) LayoutInflater.from(this).inflate(R.layout.rsweb_web_layout,null);

        mWebView.loadUrl("http://192.168.1.101/thinkphp/Sample_Mjmz/web/madridNews?id=1");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new JavaInterface(),"rsWeb");

        mAdapter = new MyRecyclerViewAdapter(Datas.getImagesUrlArray());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.onPause();
        mWebView.removeAllViews();
        mWebView = null;
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private final int TYPE_WEB = 0x0000;
        private final int TYPE_ITEM = 0x0001;

        private ArrayList<String> adapterList;

        public MyRecyclerViewAdapter(ArrayList<String> list) {
            this.adapterList = list;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0){
                return TYPE_WEB;
            }else {
                return TYPE_ITEM;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_WEB) {
                return new WebViewHolder(mWebView);
            }else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recyclerview_item,parent,false);
                return new MyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            if(viewType == TYPE_ITEM) {
                ((MyViewHolder)holder).bindVH(position);
            }else {

            }
        }

        @Override
        public int getItemCount() {
            if(adapterList != null) {
                return adapterList.size() + 1;
            }
            return 0;
        }

        private class WebViewHolder extends RecyclerView.ViewHolder {
            public WebViewHolder(View itemView) {
                super(itemView);
                mWebView = (WebView) itemView;
            }
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
                ImageWrapper.getInstance().with(this.itemView.getContext()).setUrl(adapterList.get(position % adapterList.size())).setImageView(mImageView);
                mTitle.setText("商品--" + position);
                mSubTitle.setText("地址--" + position);
            }
        }
    }

    public class JavaInterface {
        @JavascriptInterface
        public void imageOnClick(String[] imageUrlList,int index) {
            Log.e("yy",index+"---"+
                    imageUrlList[index]);
            ToastUtil.setToast(getApplicationContext(),imageUrlList[index]);
        }

        @JavascriptInterface
        public void webLoadComplete(final int webHeight) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.setToast(getApplicationContext(),"网页加载成功--->>height=" + DisplayUtils.dip2px(webHeight));
//                    ViewGroup.LayoutParams params = mWebView.getLayoutParams();
//                    params.height = DisplayUtils.dip2px(webHeight);
//                    Log.e("yy","height="+params.height);
//                    mWebView.setLayoutParams(params);
                }
            });
        }
    }
}
