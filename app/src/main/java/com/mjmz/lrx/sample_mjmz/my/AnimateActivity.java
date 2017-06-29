package com.mjmz.lrx.sample_mjmz.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mjmz.lrx.sample_mjmz.R;
import com.mjmz.lrx.sample_mjmz.base.BaseActivity;

/**
 * Created by liurunxiong on 2017/6/29.
 */

public class AnimateActivity extends BaseActivity {
    private Button mValueAnimateBtn;
    private Button mObjectAnimateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);

        init();
    }

    private void init() {
        mValueAnimateBtn = (Button) findViewById(R.id.valueAnimateBtn);
        mObjectAnimateBtn = (Button) findViewById(R.id.objectAnimateBtn);

        mValueAnimateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimateActivity.this,ValueAnimateActivity.class);
                startActivity(intent);
            }
        });

        mObjectAnimateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimateActivity.this,ObjectAnimateActivity.class);
                startActivity(intent);
            }
        });
    }
}
