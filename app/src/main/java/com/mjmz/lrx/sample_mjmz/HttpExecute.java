package com.mjmz.lrx.sample_mjmz;

import android.app.Application;
import android.util.Log;

import com.example.lrx.httpwrapper.AbsHttpExecute;
import com.example.lrx.httpwrapper.HttpParams;
import com.example.lrx.httpwrapper.HttpResultListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by liurunxiong on 2017/6/28.
 */

public class HttpExecute extends AbsHttpExecute {
    public HttpExecute(Application application) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        File file = new File(application.getExternalCacheDir(),"okGo");
        builder.cache(new Cache(file,20*1024*1024));
        OkGo.getInstance().init(application)
        .setOkHttpClient(builder.build())
        .setRetryCount(3);
    }

    @Override
    public void execute(HttpParams params, final HttpResultListener listener) {
        if(params.method == HttpParams.Method.NOMAL_GET) {//get方法
            OkGo.<String>get(params.url)
                    .tag(params.tag)
                    .cacheMode(!params.cache? CacheMode.NO_CACHE:CacheMode.REQUEST_FAILED_READ_CACHE)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            if(listener != null) {
                                listener.onSuccess(response.body());
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            super.onError(response);
                            if(listener != null) {
                                listener.onFailure(response.message());
                            }
                        }
                    });
        }else if (params.method == HttpParams.Method.DOWN_GET) {//get方法下载
            OkGo.<File>get(params.url)
                    .tag(params.tag)
                    .execute(new FileCallback() {
                        @Override
                        public void onSuccess(Response<File> response) {
                            if(listener != null) {
                                try {
                                    FileInputStream stream = new FileInputStream(response.body());
                                    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
                                    byte[] b = new byte[1000];
                                    int n;
                                    while ((n = stream.read(b)) != -1)
                                        out.write(b, 0, n);
                                    stream.close();
                                    out.close();
                                    listener.onSuccess(out.toByteArray());
                                } catch (IOException e){
                                }
                            }
                        }

                        @Override
                        public void onError(Response<File> response) {
                            super.onError(response);
                            if (listener != null) {
                                listener.onFailure(response.message());
                            }
                        }

                        @Override
                        public void downloadProgress(Progress progress) {
                            super.downloadProgress(progress);
                            if(listener != null) {
                                listener.downloadProgress(progress.currentSize,progress.totalSize,progress.fraction);
                            }
                        }
                    });
        }else if (params.method == HttpParams.Method.POST) {//post方法,主要用于上传
            PostRequest postRequset = OkGo.post(params.url);
            if(params.params == null) {params.params = new HashMap<>();};
            Iterator<String> iter = params.params.keySet().iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                postRequset.params(key,params.params.get(key));
            }

            if(params.fileMap == null) {params.fileMap = new HashMap<>();};
            Iterator<String> iter2 = params.fileMap.keySet().iterator();
            while (iter2.hasNext()) {
                String key = iter2.next();
                postRequset.params(key,params.fileMap.get(key));
            }

            postRequset.tag(params.tag);
            postRequset.isMultipart(true);
            postRequset.execute(new StringCallback() {

                @Override
                public void onSuccess(Response<String> response) {
                    if (listener != null) {
                        listener.onSuccess(response.body());
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    if(listener != null) {
                        listener.onFailure(response.message());
                    }
                }

                @Override
                public void uploadProgress(Progress progress) {
                    super.uploadProgress(progress);
                    if(listener != null) {
                        listener.upProgress(progress.currentSize,progress.totalSize,progress.fraction);
                    }
                }
            });
        }
    }

    @Override
    public void cancelRequest(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }
}
