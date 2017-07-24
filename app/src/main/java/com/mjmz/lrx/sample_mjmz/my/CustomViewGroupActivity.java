package com.mjmz.lrx.sample_mjmz.my;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;
import com.mjmz.lrx.sample_mjmz.common.ToastUtil;
import com.mjmz.lrx.sample_mjmz.customeview.FlowLayout;

import java.util.Random;

/**
 * Created by Administrator on 2017/7/21.
 */

public class CustomViewGroupActivity extends BaseActivity {
    private FlowLayout mFlowLayout;
    private Button mRefreshBtn;

    private int mItemCount = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_viewgroup);

        init();
    }

    private void init() {
        mFlowLayout = (FlowLayout) findViewById(R.id.flowLayout);
        mRefreshBtn = (Button) findViewById(R.id.refreshBtn);

        final FlowLayout.FlowLayoutAdapter adapter = new FlowLayout.FlowLayoutAdapter() {
            @Override
            public int getItemCount() {
                return mItemCount;
            }

            @Override
            public View getView(final int index, FlowLayout flowLayout) {
                View view = LayoutInflater.from(CustomViewGroupActivity.this)
                        .inflate(R.layout.flowlayout_item_view,null);
                TextView tv = (TextView) view.findViewById(R.id.flowLayout_item_textview);
                if(index % 2 == 0) {
                    tv.setText("welcome--" + index);
                }else {
                    tv.setText("楚心-" + index);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.setToast(CustomViewGroupActivity.this,"点击了=" + index);
                    }
                });
                return view;
            }
        };

        mFlowLayout.setAdapter(adapter);

        mRefreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                mItemCount = random.nextInt(50);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
