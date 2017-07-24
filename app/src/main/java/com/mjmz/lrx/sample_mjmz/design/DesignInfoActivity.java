package com.mjmz.lrx.sample_mjmz.design;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lrx.imagewrapper.ImageWrapper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx2.adapter.ObservableBody;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.Datas;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;
import com.mjmz.lrx.sample_mjmz.customeview.RecyclerScrollView;
import com.mjmz.lrx.sample_mjmz.my.OkGoRxjavaActivity;
import com.mjmz.lrx.sample_mjmz.tools.DisplayUtils;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.callback.OnImageClickListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/19.
 */

public class DesignInfoActivity extends BaseActivity {
    //控件类
    private RecyclerScrollView mScrollView;
    private TextView mTextView;
    private RecyclerView mRecyclerView;
    private RichText mRichText;

    //数据类
    private static final String IMAGE1 = "<h1>RichText</h1><p>Android平台下的富文本解析器</p><img title=\"\" src=\"http://image.tianjimedia.com/uploadImages/2015/129/56/J63MI042Z4P8.jpg\"  style=\"cursor: pointer;\"><br><br>" +
            "<h3><font color='#32b7b9'>点击菜单查看更多</h3>Demo<img src=\"http://ww2.sinaimg.cn/bmiddle/813a1fc7jw1ee4xpejq4lj20g00o0gnu.jpg\" /><p><a href=\"http://www.baidu.com\">baidu</a>" +
            "hello asdkjfgsduk <a href=\"http://www.jd.com\">jd</a></p>";

    private MyRecyclerViewAdapter mAdapter;

    private InterfaceApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designinfo);

        //初始化
        init();
    }

    private void init() {
        //创建数据
        mApi = new InterfaceApi();

        //找寻控件
        mScrollView = (RecyclerScrollView) findViewById(R.id.nestScrollView);
        mScrollView.setOffsetY(0);
        mTextView = (TextView) findViewById(R.id.designinfo_content_textView);
        mRecyclerView = (RecyclerView) findViewById(R.id.designinfo_recyclerView);

        //设置数据和监听
        mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.dip2px(300)));
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

        //获取本地服务器getHtml方法
        mApi.getHtml().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONObject data = object.getJSONObject("data").getJSONObject("model");
                            String fullHtml = data.getString("html");

                            mRichText = RichText.from(fullHtml);
                            mRichText.imageClick(new OnImageClickListener() {
                                @Override
                                public void imageClicked(List<String> imageUrls, int position) {
                                    ToastUtil.setToast(getApplicationContext(),position + "-->>" + imageUrls.get(position));
                                }
                            });
                            mRichText.into(mTextView);

                        }catch (Exception e) {
                            Log.e("yy",e.toString());
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("yy",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

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
            Log.e("yy","viewHolder=" + position);
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
                Log.e("yy","bind--" + position);
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

    public class InterfaceApi {
        /**
         * 本地搭建服务器Add方法
         * @return
         */
        public Observable<String> getHtml() {
            return OkGo.<String>get("http://192.168.1.101/thinkphp/Sample_Mjmz/test/getHtml?id=1&type=1")
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .cacheKey("getHtml")
                    .tag(this)
                    .cacheTime(60*1000)
                    .converter(new StringConvert())
                    .adapt(new ObservableBody<String>());
        }
    }
}
