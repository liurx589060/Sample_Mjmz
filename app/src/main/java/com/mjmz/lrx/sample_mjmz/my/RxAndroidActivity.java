package com.mjmz.lrx.sample_mjmz.my;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lrx.httpwrapper.HttpParams;
import com.example.lrx.httpwrapper.HttpRequset;
import com.example.lrx.httpwrapper.httpexecute.DefaultGetResultListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.tools.GlobalToolsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

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
    private Button mCacheRequestBtn;
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
        mCacheRequestBtn = (Button) findViewById(R.id.cacheRequestBtn);
        mResultTextView = (TextView) findViewById(R.id.result);

        mNetRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getNetRequestByRxAndroid("单个请求",url).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<JSONObject>() {
//                            @Override
//                            public void onCompleted() {
//                                Log.e("yy","RxAndroid--onCompleted");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e("yy","RxAndroid--onError--" + e.toString());
//                            }
//
//                            @Override
//                            public void onNext(JSONObject jsonObject) {
//                                Log.e("yy","RxAndroid--onNext");
//                                String originStr = mResultTextView.getText().toString();
//                                mResultTextView.setText(originStr + jsonObject.toString());
//                            }
//                        });

                getWeather().subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<JSONObject>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                addDisposeable(d);
                            }

                            @Override
                            public void onNext(@NonNull JSONObject jsonObject) {
                                Log.e("yy","RxAndroid--onNext");
                                String originStr = mResultTextView.getText().toString();
                                mResultTextView.setText(originStr + jsonObject.toString());
//                                String radar = jsonObject.weatherinfo.city;
//                                mResultTextView.setText(radar);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.e("yy","RxAndroid--onError--" + e.toString());
                            }

                            @Override
                            public void onComplete() {
                                Log.e("yy","RxAndroid--onCompleted");
                            }
                        });

                //错误重试
//                getWeather().subscribeOn(Schedulers.io())
//                        .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
//                            @Override
//                            public Observable<?> call(Observable<? extends Throwable> observable) {
//                                return observable.flatMap(new Func1<Throwable, Observable<?>>() {
//                                    @Override
//                                    public Observable<?> call(Throwable throwable) {
//                                        Log.e("yy","retryWhen=" + throwable.toString());
//                                        return Observable.just(1);
//                                    }
//                                });
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new Subscriber<JSONObject>() {
//                            @Override
//                            public void onCompleted() {
//                                Log.e("yy","RxAndroid--onCompleted");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e("yy","RxAndroid--onError--" + e.toString());
//                            }
//
//                            @Override
//                            public void onNext(JSONObject jsonObject) {
//                                Log.e("yy","RxAndroid--onNext");
//                                String originStr = mResultTextView.getText().toString();
//                                mResultTextView.setText(originStr + jsonObject.toString());
////                                String radar = jsonObject.weatherinfo.city;
////                                mResultTextView.setText(radar);
//                            }
//                        });

            }
        });

        mChainRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRequestByRxAndroid2("链式调用1--getNetRequestByRxAndroid2",url).subscribeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<JSONObject>>() {
                    @Override
                    public ObservableSource<JSONObject> apply(@NonNull String s) throws Exception {
                        return getNetRequestByRxAndroid(s,url2);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        addDisposeable(d);
                    }

                    @Override
                    public void onNext(@NonNull JSONObject o) {
                        String originStr = mResultTextView.getText().toString();
                        mResultTextView.setText(originStr + o.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                addDisposeable(d);
                            }

                            @Override
                            public void onNext(@NonNull Object o) {
                                String str = mResultTextView.getText().toString();
                                if(o instanceof JSONObject) {
                                    mResultTextView.setText(str + "\n\n" + "天气" + "\n" + o.toString());
                                }else if (o instanceof String) {
                                    mResultTextView.setText(str + "\n\n" + "wnw" + "\n" + o);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

        mZipRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNetRequestByRxAndroid("天气",url)
                        .zipWith(getNetRequestByRxAndroid2("wnw", url2), new BiFunction<JSONObject, String, String>() {
                            @Override
                            public String apply(@NonNull JSONObject jsonObject, @NonNull String s) throws Exception {
                                return new String(jsonObject.toString() + "\n\n" + s);
                            }
                        } )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                addDisposeable(d);
                            }

                            @Override
                            public void onNext(@NonNull String s) {
                                if(s instanceof String) {
                                    mResultTextView.setText(s);
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

        mCacheRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getModelInfo().subscribeOn(Schedulers.io())
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
    }

    private Observable<JSONObject> getNetRequestByRxAndroid(final String str,final String url) {
        Observable<JSONObject> object = Observable.create(new ObservableOnSubscribe<JSONObject>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<JSONObject> e) throws Exception {
                HttpParams params = new HttpParams();
                params.setMethod(HttpParams.Method.NOMAL_GET);
                params.setUrl(url);
                HttpRequset.getInstance().execute(params, new DefaultGetResultListener() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            e.onNext(json);
                            mResultTextView.setText(str + "\n");
                            e.onComplete();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String failMessage) {
                        e.onError(new Throwable(failMessage));
                    }
                });
            }

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
        });
        return object;
    }

    private Observable<String> getNetRequestByRxAndroid2(final String str,final String url) {
        Observable<String> object = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                HttpParams params = new HttpParams();
                params.setMethod(HttpParams.Method.NOMAL_GET);
                params.setUrl(url);
                HttpRequset.getInstance().execute(params, new DefaultGetResultListener() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            e.onNext(response);
                            e.onComplete();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String failMessage) {
                        e.onError(new Throwable(failMessage));
                    }
                });
            }
        });
        return object;
    }

    public Observable<JSONObject> getWeather() {
        File cacheFile = this.getExternalCacheDir();
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);//缓存文件为10MB

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        builder.addNetworkInterceptor(new CacheInterceptor(1*60,3*60));
        builder.addInterceptor(new CacheInterceptor(1*60,3*60));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/adat/sk/")
                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new JsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
        RequestApi api = retrofit.create(RequestApi.class);
        return api.getWeatherInterface();
    }

    public Observable<String> getModelInfo() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        File cacheFile = this.getExternalCacheDir();
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);//缓存文件为10MB

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cache(cache);
        builder.addNetworkInterceptor(new CacheInterceptor(0,60));
        builder.addInterceptor(new CacheInterceptor(0,60));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/adat/sk/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
        RequestApi api = retrofit.create(RequestApi.class);
        return api.getModelInfoInterface();
    }



    public interface RequestApi {
        @GET("http://www.weather.com.cn/adat/sk/101010100.html")
        Observable<JSONObject> getWeatherInterface();

        @GET("https://www.wnwapi.com/wnwapi/index.php/Api/Index/getGoodsInfo?equipment=mobile&product_type=4&product_version=0.6.0" +
                "&client_type=5&client_version=10.2.1&user_city=shenzhen&channel_id=wnw&type=2&&goods_id=13903&partner_id=86")
        Observable<String> getModelInfoInterface();
    }

    public class Weather<T> {
        public T weatherinfo;
    }

    public class Info {
        public String city;
        public String cityid;
        public String temp;
        public String WD;
        public String WS;
        public String SD;
        public String WSE;
        public String time;
        public String isRadar;
        public String Radar;
        public String njd;
        public String qy;
    }

    /**
     * 自定义转换器
     */
    public class JsonConverterFactory extends Converter.Factory {

        public JsonConverterFactory create() {
            return new JsonConverterFactory ();
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return new JsonResponseBodyConverter<JSONObject>();
        }

//        @Override
//        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//            return  new JsonResponseBodyConverter<JSONObject>();
//        }


    }


    public class JsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        public  JsonResponseBodyConverter() {

        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            JSONObject jsonObj;
            try {
                jsonObj = new JSONObject(value.string());
                return (T) jsonObj;
            } catch(JSONException e) {
                return null;
            }
        }
    }

    //缓存用
    public class CacheInterceptor implements Interceptor {
        private int maxAge;
        private int maxStale;

        public CacheInterceptor(int maxAge,int maxStale) {
            this.maxAge = maxAge;
            this.maxStale = maxStale;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!GlobalToolsUtil.isConnect(RxAndroidActivity.this)) {//没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            Response responseLatest;
            if (GlobalToolsUtil.isConnect(RxAndroidActivity.this)) {
                int maxAge = this.maxAge; //有网失效一分钟
                responseLatest = response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = this.maxStale; // 没网失效6小时
                responseLatest= response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return  responseLatest;
        }
    }
}
