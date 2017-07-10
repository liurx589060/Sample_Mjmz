
package com.mjmz.lrx.sample_mjmz.my;

import android.app.Activity;
import android.os.Bundle;

import com.mjmz.lrx.sample_mjmz.R;

public class XfermodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(new AvoidXfermodeView(getApplicationContext()));
        // setContentView(new PorterDuffXfermodeView(getApplicationContext()));
        setContentView(R.layout.activity_xfermode);
    }
}
