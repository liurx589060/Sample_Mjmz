package com.mjmz.lrx.sample_mjmz.my;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx2.adapter.ObservableBody;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.tools.GlobalToolsUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liurunxiong on 2017/7/7.
 */

public class EncryptionActivity extends BaseActivity {
    private Button mDecryptionBtn;
    private Button mEncryptionBtn;
    private TextView mResultTextView;

    private String mEncryptionText;
    private String mEncryptionKey;

    private  final  String url = "http://www.weather.com.cn/adat/sk/101010100.html";
    private String url2 = "https://www.wnwapi.com/wnwapi/index.php/Api/Index/getGoodsInfo?equipment=mobile&product_type=4&product_version=0.6.0" +
            "&client_type=5&client_version=10.2.1&user_city=shenzhen&channel_id=wnw&type=2&&goods_id=13903&partner_id=86";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        init();
    }

    private void init() {
        mDecryptionBtn = (Button) findViewById(R.id.decryptionBtn);
        mEncryptionBtn = (Button) findViewById(R.id.encryptionBtn);
        mEncryptionBtn.setVisibility(View.GONE);
        mResultTextView = (TextView) findViewById(R.id.resultText);

        //请求网络数据
//        getWeather().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        addDisposeable(d);
//                    }
//
//                    @Override
//                    public void onNext(@NonNull String s) {
//                        try {
//                            mEncryptionText = GlobalToolsUtil.encrypt("weather",s);
//                            mResultTextView.setText(mEncryptionText);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Log.e("yy",e.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.e("yy",e.toString());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        getModelInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposeable(d);
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        try {
                            mEncryptionKey = "modelInfo";
                            mEncryptionText = GlobalToolsUtil.encrypt(mEncryptionKey,s);
                            mResultTextView.setText(mEncryptionText);
                        } catch (Exception e) {
                            e.printStackTrace();
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

        mDecryptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEncryptionText == null || mEncryptionKey == null) {
                    return;
                }

                try {
                    mResultTextView.setText(GlobalToolsUtil.decrypt(mEncryptionKey,mEncryptionText));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("yy",e.toString());
                }
            }
        });

        mEncryptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEncryptionKey = "7824sdferdfgdtr";
                try {
                    mEncryptionText = GlobalToolsUtil.encrypt(mEncryptionKey,mResultTextView.getText().toString());
                    mResultTextView.setText(GlobalToolsUtil.encrypt(mEncryptionKey,mEncryptionText));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("yy",e.toString());
                }
            }
        });
    }

    private Observable<String> getWeather() {
        return OkGo.<String>get(url)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("encryption_weather")
                .cacheTime(3*60*60*1000)
                .tag(this)
                .converter(new StringConvert())
                .adapt(new ObservableBody<String>());
    }

    private Observable<String> getModelInfo() {
        return OkGo.<String>get(url2)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("encryption_weather")
                .cacheTime(3*60*60*1000)
                .tag(this)
                .converter(new StringConvert())
                .adapt(new ObservableBody<String>());
    }
}
