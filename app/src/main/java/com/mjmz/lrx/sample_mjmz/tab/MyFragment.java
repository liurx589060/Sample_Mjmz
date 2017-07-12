package com.mjmz.lrx.sample_mjmz.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseFragment;
import com.mjmz.lrx.sample_mjmz.language.AppButton;
import com.mjmz.lrx.sample_mjmz.my.AnimateActivity;
import com.mjmz.lrx.sample_mjmz.my.EncryptionActivity;
import com.mjmz.lrx.sample_mjmz.my.HotPicActivity;
import com.mjmz.lrx.sample_mjmz.my.MyLanguageActivity;
import com.mjmz.lrx.sample_mjmz.my.MyNotifyActivity;
import com.mjmz.lrx.sample_mjmz.my.OkGoRxjavaActivity;
import com.mjmz.lrx.sample_mjmz.my.RxAndroidActivity;
import com.mjmz.lrx.sample_mjmz.my.TransformWebViewActivity;

/**
 * Created by liurunxiong on 2017/3/10.
 */

public class MyFragment extends BaseFragment {
    //控件类
    private Button mNotifyBtn;
    private AppButton mLanguageBtn;
    private Button mRxAndroidBtn;
    private Button mOkGoRxjavaBtn;
    private EditText mEditText;
    private Button mSwitchToPasswordBtn;
    private Button mWebViewBtn;
    private Button mAnimateBtn;
    private Button mEncyptionBtn;
    private Button mHotPicBtn;

    //数据类
    private boolean isHidden;

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
        mOkGoRxjavaBtn = (Button) rootView.findViewById(R.id.okGo_Rxjava);
        mEditText = (EditText) rootView.findViewById(R.id.editText);
        mSwitchToPasswordBtn = (Button) rootView.findViewById(R.id.swichToPassword);
        mWebViewBtn = (Button) rootView.findViewById(R.id.webviewBtn);
        mAnimateBtn = (Button) rootView.findViewById(R.id.animateBtn);
        mEncyptionBtn = (Button) rootView.findViewById(R.id.encryptionBtn);
        mHotPicBtn = (Button) rootView.findViewById(R.id.hotPicBtn);

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

        mOkGoRxjavaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OkGoRxjavaActivity.class);
                startActivity(intent);
            }
        });

        mWebViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransformWebViewActivity.class);
                startActivity(intent);
            }
        });

        mHotPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),HotPicActivity.class);
                startActivity(intent);
            }
        });

        mAnimateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AnimateActivity.class);
                startActivity(intent);
            }
        });

        mEncyptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EncryptionActivity.class);
                startActivity(intent);
            }
        });


        mSwitchToPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    //设置EditText文本为可见的
//                    mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD|InputType.TYPE_CLASS_TEXT);
                } else {
                    //设置EditText文本为隐藏的
//                    mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                isHidden = !isHidden;
                mEditText.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = mEditText.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });

    }
}
