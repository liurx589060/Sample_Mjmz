package com.mjmz.lrx.sample_mjmz.my;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lrx.httpwrapper.HttpParams;
import com.example.lrx.httpwrapper.HttpRequset;
import com.example.lrx.httpwrapper.httpexecute.DefaultGetResultListener;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by liurunxiong on 2017/5/5.
 * 简单使用RxAndroid
 */

public class RxAndroidActivity extends BaseActivity {
    //控件类
    private Button mNetRequestBtn;
    private Button mChainRequestBtn;
    private Button mMergeRequestBtn;
    private Button mZipRequestBtn;
    private TextView mResultTextView;

    //数据类
    String url = "http://www.weather.com.cn/adat/sk/101010100.html";
    String url2 = "https://www.wnwapi.com/wnwapi/index.php/Api/Index/getGoodsInfo?equipment=mobile&product_type=4&product_version=0.6.0" +
            "&client_type=5&client_version=10.2.1&user_city=shenzhen&channel_id=wnw&type=2&&goods_id=13903&partner_id=86";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxandroid);

        //初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mNetRequestBtn = (Button) findViewById(R.id.rxAndroid_netRequest_btn);
        mChainRequestBtn = (Button) findViewById(R.id.chainRequset);
        mMergeRequestBtn = (Button) findViewById(R.id.mergeBtn);
        mZipRequestBtn = (Button) findViewById(R.id.zipRequest);
        mResultTextView = (TextView) findViewById(R.id.result);

        mNetRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRequestByRxAndroid("单个请求",url).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<JSONObject>() {
                            @Override
                            public void onCompleted() {
                                Log.e("yy","RxAndroid--onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("yy","RxAndroid--onError--" + e.toString());
                            }

                            @Override
                            public void onNext(JSONObject jsonObject) {
                                Log.e("yy","RxAndroid--onNext");
                                String originStr = mResultTextView.getText().toString();
                                mResultTextView.setText(originStr + jsonObject.toString());
                            }
                        });
            }
        });

        mChainRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRequestByRxAndroid2("链式调用1--getNetRequestByRxAndroid2",url).subscribeOn(Schedulers.io())
                .flatMap(new Func1<String, Observable<JSONObject>>() {
                    @Override
                    public Observable<JSONObject> call(String s) {
                        return getNetRequestByRxAndroid(s,url2);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONObject>() {
                    @Override
                    public void call(JSONObject jsonObject) {
                        String originStr = mResultTextView.getText().toString();
                        mResultTextView.setText(originStr + jsonObject.toString());
                    }
                });
            }
        });

        mMergeRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.merge(getNetRequestByRxAndroid("天气",url),getNetRequestByRxAndroid2("wnw",url2))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object o) {
                                String str = mResultTextView.getText().toString();
                                if(o instanceof JSONObject) {
                                    mResultTextView.setText(str + "\n\n" + "天气" + "\n" + o.toString());
                                }else if (o instanceof String) {
                                    mResultTextView.setText(str + "\n\n" + "wnw" + "\n" + o);
                                }
                            }
                        });
            }
        });

        mZipRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRequestByRxAndroid("天气",url)
                        .zipWith(getNetRequestByRxAndroid2("wnw", url2), new Func2<JSONObject, String, String>() {
                            @Override
                            public String call(JSONObject jsonObject, String s) {
                                return new String(jsonObject.toString() + "\n\n" + s);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                mResultTextView.setText(s);
                            }
                        });
            }
        });
    }

    private Observable<JSONObject> getNetRequestByRxAndroid(final String str,final String url) {
        Observable<JSONObject> object = Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(final Subscriber<? super JSONObject> subscriber) {
                HttpParams params = new HttpParams();
                params.setMethod(HttpParams.Method.NOMAL_GET);
                params.setUrl(url);
                HttpRequset.getInstance().execute(params, new DefaultGetResultListener() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            subscriber.onNext(json);
                            mResultTextView.setText(str + "\n");
                            subscriber.onCompleted();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String failMessage) {
                        subscriber.onError(new IOException());
                    }
                });

                //轮询
//                Schedulers.newThread().createWorker()
//                        .schedulePeriodically(new Action0() {
//                            @Override
//                            public void call() {
//                                HttpParams params = new HttpParams();
//                                params.setMethod(HttpParams.Method.NOMAL_GET);
//                                params.setUrl(url);
//                                HttpRequset.getInstance().execute(params, new DefaultGetResultListener() {
//                                    @Override
//                                    public void onSuccess(String response) {
//                                        try {
//                                            JSONObject json = new JSONObject(response);
//                                            subscriber.onNext(json);
//                                            mResultTextView.setText(str + "\n");
//                                        } catch (JSONException e1) {
//                                            e1.printStackTrace();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(String failMessage) {
//                                        subscriber.onError(new IOException());
//                                    }
//                                });
//                            }
//                        },100,3000,TimeUnit.MILLISECONDS);
            }
        });
        return object;
    }

    private Observable<String> getNetRequestByRxAndroid2(final String str,final String url) {
        Observable<String> object = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                HttpParams params = new HttpParams();
                params.setMethod(HttpParams.Method.NOMAL_GET);
                params.setUrl(url);
                HttpRequset.getInstance().execute(params, new DefaultGetResultListener() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            subscriber.onNext(response);
                            subscriber.onCompleted();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String failMessage) {
                        subscriber.onError(new IOException());
                    }
                });
            }
        });
        return object;
    }
}
