package com.mjmz.lrx.sample_mjmz.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.mjmz.lrx.sample_mjmz.language.AppButton;
import com.mjmz.lrx.sample_mjmz.my.MyLanguageActivity;
import com.mjmz.lrx.sample_mjmz.my.MyNotifyActivity;
import com.mjmz.lrx.sample_mjmz.my.RxAndroidActivity;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class MyFragment extends BaseFragment {
    //控件类
    private Button mNotifyBtn;
    private AppButton mLanguageBtn;
    private Button mRxAndroidBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my,null);

        //初始化
        init(rootView);

        return rootView;
    }

    /**
     * 初始化
     * @param rootView
     */
    private void init(View rootView) {
        //找寻控件
        mNotifyBtn = (Button) rootView.findViewById(R.id.notify);
        mLanguageBtn = (AppButton) rootView.findViewById(R.id.language);
        mRxAndroidBtn = (Button) rootView.findViewById(R.id.RxAndroid);

        //设置数据和监听
        mNotifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyNotifyActivity.class);
                startActivity(intent);
            }
        });

        mLanguageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyLanguageActivity.class);
                startActivity(intent);
            }
        });

        mRxAndroidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RxAndroidActivity.class);
                startActivity(intent);
            }
        });

    }
}
