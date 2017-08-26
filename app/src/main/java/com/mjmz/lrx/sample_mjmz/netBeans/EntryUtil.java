package com.mjmz.lrx.sample_mjmz.netBeans;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/8/25.
 */

public class EntryUtil {
    private static EntryUtil instance = new EntryUtil();

    public static EntryUtil getInstance() {
        return instance;
    }

    public <T,K> K createInstance(T t,Class<K> cls) {
        if(t == null) {
            K obj=null;
            try {
                obj=cls.newInstance();
            } catch (Exception e) {
                obj=null;
                Log.e("yy",e.toString());
            }
            return obj;
        }else {
            return (K) t;
        }
    }

//    public  <T> String test(T t) {
//        if(t == null) {
//            return "null";
//        }
//        return (String) t;
//    }
}
