package com.mjmz.lrx.sample_mjmz.my;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.convert.Converter;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okrx2.adapter.ObservableBody;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.BossUrl;
import com.mjmz.lrx.sample_mjmz.common.Const;
import com.mjmz.lrx.sample_mjmz.tools.GlobalToolsUtil;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import okhttp3.Response;

/**
 * Created by liurunxiong on 2017/6/28.
 */

public class OkGoRxjavaActivity extends BaseActivity {
    String url = "http://www.weather.com.cn/adat/sk/101010100.html";
    String url2 = "https://www.wnwapi.com/wnwapi/index.php/Api/Index/getGoodsInfo?equipment=mobile&product_type=4&product_version=0.6.0" +
            "&client_type=5&client_version=10.2.1&user_city=shenzhen&channel_id=wnw&type=2&&goods_id=13903&partner_id=86";

    //控件
    private Button mNormalRequestBtn;
    private Button mCacheReuqestBtn;
    private Button mLocalAddRequestBtn;
    private TextView mResultTextView;

    private InterfaceApi mApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okgorxjava);

        init();
    }

    private void init() {
        mApi = new InterfaceApi();

        //找寻控件
        mNormalRequestBtn = (Button) findViewById(R.id.normalRequest);
        mCacheReuqestBtn = (Button) findViewById(R.id.cacheRequest);
        mLocalAddRequestBtn = (Button) findViewById(R.id.localAddRequest);
        mResultTextView = (TextView) findViewById(R.id.resultText);

        mNormalRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApi.getWeather().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JSONObject>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                addDisposeable(d);
                            }

                            @Override
                            public void onNext(@NonNull JSONObject jsonObject) {
                                mResultTextView.setText(jsonObject.toString());
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
        });

        mCacheReuqestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApi.getModelInfo().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                addDisposeable(d);
                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                mResultTextView.setText(s);
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
        });

        mLocalAddRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApi.getLocalAdd().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                addDisposeable(d);
                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                try {
                                    mResultTextView.setText(s);
                                    JSONObject object = new JSONObject(s);
                                    SharedPreferences sp = GlobalToolsUtil.getSharedPreferences(getApplicationContext());
                                    if(object.has("token")) {
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString(Const.SP.SP_TOKEN,object.getString("token"));
                                        editor.commit();
                                    }
                                }catch (Exception e) {
                                    e.toString();
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
        });
    }

    public class InterfaceApi {
        public Observable<JSONObject> getWeather() {
            return OkGo.<JSONObject>get(url)
                    .cacheMode(CacheMode.NO_CACHE)
                    .tag(this)
                    .converter(new JsonConvert())
                    .adapt(new ObservableBody<JSONObject>());
        }

        public Observable<String> getModelInfo() {
            return OkGo.<String>get(url2)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .cacheKey("modelInfo")
                    .tag(this)
                    .cacheTime(60*1000)
                    .converter(new StringConvert())
                    .adapt(new ObservableBody<String>());
        }

        /**
         * 本地搭建服务器Add方法
         * @return
         */
        public Observable<String> getLocalAdd() {
            String method = "getUserDataSg";
//            String method = "getHtml";
//            String url3 = BossUrl.getServiceBossUrl() + "test/" + method + "?id=1&" + BossUrl.getParamsStr(method) + "&type=1";
            String url3 = BossUrl.getServiceBossUrl() + "test/" + method + "?" + BossUrl.getParamsStr(method) + "&type=1";
            Log.e("yy",url3);
            return OkGo.<String>get(url3)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .cacheKey("LocalAdd")
                    .tag(this)
                    .cacheTime(60*1000)
                    .converter(new StringConvert())
                    .adapt(new ObservableBody<String>());
        }
    }

    public class JsonConvert implements Converter<JSONObject> {
        @Override
        public JSONObject convertResponse(Response response) throws Throwable {
            JSONObject object = null;
            try {
                object = new JSONObject(response.body().string());
            }catch (Exception e) {
                Log.e("yy",e.toString());
                e.toString();
            }
            return object;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
