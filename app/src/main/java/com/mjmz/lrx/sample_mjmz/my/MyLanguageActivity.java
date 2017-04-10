package com.mjmz.lrx.sample_mjmz.my;

import android.os.Bundle;
import android.view.View;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;;
import com.mjmz.lrx.sample_mjmz.language.AppButton;
import com.mjmz.lrx.sample_mjmz.language.StringUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by liurunxiong on 2017/4/7.
 */

public class MyLanguageActivity extends BaseActivity implements View.OnClickListener{
    //控件类
    private AppButton mEnglishBtn;
    private AppButton mChineseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_language);

        //初始化
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //找寻控件
        mEnglishBtn = (AppButton) findViewById(R.id.english);
        mChineseBtn = (AppButton) findViewById(R.id.chinese);

        //设置监听
        mEnglishBtn.setOnClickListener(this);
        mChineseBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.english:
                StringUtil.switchLanguage(MyLanguageActivity.this,StringUtil.ENGLISH);
                break;

            case R.id.chinese:
                StringUtil.switchLanguage(MyLanguageActivity.this,StringUtil.CHINESE);
                break;
        }

        String event = "swith language";
        EventBus.getDefault().post(event);

//        Intent intent = new Intent(MyLanguageActivity.this, TabMainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }
}
