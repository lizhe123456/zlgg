package com.zlcm.zlgg.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.lib.LocationTask;


/**
 * Created by lizhe on 2017/12/26.
 * 类介绍：
 */

public class GuideActivity extends Activity {

    private Handler handler;
    private Runnable  runnable = new Runnable(){

        @Override
        public void run() {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            GuideActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        init();
        setData();
    }


    private void init() {

    }

    private void setData() {
        LocationTask.getInstance(this).startLocate();
        handler = new Handler();
        handler.postDelayed(runnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
