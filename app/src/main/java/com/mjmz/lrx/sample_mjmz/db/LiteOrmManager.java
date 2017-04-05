package com.mjmz.lrx.sample_mjmz.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.mjmz.lrx.sample_mjmz.common.Const;

/**
 * Created by liurunxiong on 2017/4/5.
 */

public class LiteOrmManager {

    /**
     * 获取数据库
     * @param context
     * @param isSingle    是否为单一数据库
     * @return
     */
    public static LiteOrm getLiteOrm(Context context,boolean isSingle) {
        LiteOrm orm;
        DataBaseConfig config = new DataBaseConfig(context, Const.DB_NAME);
        config.debugged = true; // open the log
        config.dbVersion = Const.DB_VERSION; // set database version
        config.onUpdateListener = null; // set database update listener
        if(isSingle) {
            orm = LiteOrm.newSingleInstance(config);
        }else {
            orm = LiteOrm.newCascadeInstance(config);
        }
        return orm;
    }
}
