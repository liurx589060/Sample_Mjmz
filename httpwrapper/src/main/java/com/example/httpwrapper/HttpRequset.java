package com.example.httpwrapper;

import android.util.Log;

/**
 * 网络请求的借口
 * Created by liurunxiong on 2017/2/28.
 */

public class HttpRequset {

    public static AbsHttpExecute httpExcute;
    public static HttpRequset httpRequset;

    public HttpRequset() {
    }

    public static void init(AbsHttpExecute excute) {
        httpExcute = excute;
    }

    /**
     * 单例获取
     */
    public static HttpRequset getInstance() {
        try{
            if(httpExcute == null) {
                throw new NullPointerException();
            }
        }catch (Exception e) {
            Log.e("HttpRequest","请调用init方法");
        }


        if(httpRequset == null) {
            httpRequset = new HttpRequset();
        }
        return httpRequset;
    }

    /**
     * 创建一个HttpParams请求
     */
    public HttpParams setHttpMethod(HttpParams.Method method) {
        HttpParams params = new HttpParams();
        params.method = method;
        return params;
    }

    /**
     * 执行代码
     */
    public <T> void execute(HttpParams params,final HttpResultListener<T> listener) {
        if(httpExcute != null) {
            httpExcute.execute(params,listener);
        }
    }

    /**
     * 取消请求
     */
    public void cancelRequset(Object tag) {
        if(httpExcute != null) {
            httpExcute.cancelRequest(tag);
        }
    }

//    /**
//     * 普通get方法
//     */
//    public void get(String url,Object tag,Context context,final HttpResultListener<String> listener) {
//        OkGo.get(url)
//            .tag(tag)
//            .cacheMode(CacheMode.NO_CACHE)
//            .execute(new StringCallback() {
//                @Override
//                public void onSuccess(String s, Call call, Response response) {
//                    if(listener != null) {
//                        listener.onSuccess(response.toString());
//                    }
//                }
//
//                @Override
//                public void onError(Call call, Response response, Exception e) {
//                    super.onError(call, response, e);
//                    if(listener != null) {
//                        listener.onFailure(response.toString());
//                    }
//                }
//            });
//    }
//
//    /**
//     * 普通get方法
//     */
//    public void get(String url,Context context,final HttpResultListener<String> listener) {
//        OkGo.get(url)
//                .tag(context)
//                .cacheMode(CacheMode.NO_CACHE)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        if(listener != null) {
//                            listener.onSuccess(s);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        if(listener != null) {
//                            listener.onFailure(response.message());
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 下载文件
//     */
//    public void download(String url,Context context,final HttpResultListener<byte[]> listener) {
//        OkGo.get(url)
//                .tag(context)
//                .execute(new AbsCallback<byte[]>() {
//                    @Override
//                    public void onSuccess(byte[] bytes, Call call, Response response) {
//                        if (listener != null) {
//                            listener.onSuccess(bytes);
//                        }
//                    }
//
//                    @Override
//                    public byte[] convertSuccess(Response response) throws Exception {
//                        return response.body().bytes();
//                    }
//
//                    @Override
//                    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                        super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
//                        if (listener != null) {
//                            listener.downloadProgress(currentSize,totalSize,progress);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        if (listener != null) {
//                            listener.onFailure(response.message());
//                        }
//                    }
//                });
//    }
//
//    /**
//     * 上次文件
//     */
//    public void upload(String url, Context context, Map<String,String> params,Map<String,String> fileMap, final HttpResultListener<String> listener) {
//        PostRequest postRequset = OkGo.post(url);
//        Iterator<String> iter = params.keySet().iterator();
//        while (iter.hasNext()) {
//            String key = iter.next();
//            postRequset.params(key,params.get(key));
//        }
//
//        Iterator<String> iter2 = fileMap.keySet().iterator();
//        while (iter2.hasNext()) {
//            String key = iter2.next();
//            postRequset.params(key,new File(fileMap.get(key)));
//        }
//
//        postRequset.tag(context);
//        postRequset.isMultipart(true);
//        postRequset.execute(new StringCallback() {
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                if (listener != null) {
//                    listener.onSuccess(s);
//                }
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//                super.onError(call, response, e);
//                if(listener != null) {
//                    listener.onFailure(response.message());
//                }
//            }
//
//            @Override
//            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
//                super.upProgress(currentSize, totalSize, progress, networkSpeed);
//                if(listener != null) {
//                    listener.upProgress(currentSize,totalSize,progress);
//                }
//            }
//        });
//    }
}
