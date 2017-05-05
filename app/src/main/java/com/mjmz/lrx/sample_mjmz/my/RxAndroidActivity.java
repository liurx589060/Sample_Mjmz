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
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liurunxiong on 2017/5/5.
 * 简单使用RxAndroid
 */

public class RxAndroidActivity extends BaseActivity {
    //控件类
    private Button mNetRequestBtn;
    private TextView mResultTextView;

    //数据类
    String url = "http://www.weather.com.cn/adat/sk/101010100.html";

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
        mResultTextView = (TextView) findViewById(R.id.result);

        mNetRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRequestByRxAndroid().subscribeOn(Schedulers.io())
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
                                mResultTextView.setText(jsonObject.toString());
                            }
                        });
            }
        });
    }

    private Observable<JSONObject> getNetRequestByRxAndroid() {
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
            }
        });
        return object;
    }
}
