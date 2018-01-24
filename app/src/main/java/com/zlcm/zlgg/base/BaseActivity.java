package com.zlcm.zlgg.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.App;
import com.zlcm.zlgg.utils.LogUtil;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/18 0018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    protected View mView;
    protected LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        mActivity = this;
        mInflater = LayoutInflater.from(this);
        mView = mInflater.inflate(setLayout(),null);
        mUnbinder = ButterKnife.bind(mActivity);
        App.getInstance().addActivity(mActivity);
        init();
//        initFragment(savedInstanceState);
        setData();
    }


    @LayoutRes
    protected abstract int setLayout();

    protected abstract void init();

    protected abstract void setData();

    protected void initFragment(Bundle savedInstanceState){

    }


    /**
     * 防止快速点击
     *
     * @return
     */
    protected boolean avoidRepeatClick(View view){
        boolean flag = false;
        long lastTime = view.getTag(-1)==null?0:(long)view.getTag(-1);
        if (System.currentTimeMillis()-lastTime>1000){
            flag = true;
        }
        view.setTag(-1,System.currentTimeMillis());
        return flag;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
        App.getInstance().removeActivity(this);
        mUnbinder.unbind();
    }

    /**
     * 检测view是否显示
     * @param view
     * @return
     */
    protected boolean isShow(View view){
        if (view.getVisibility() == View.VISIBLE){
            return true;
        }else {
            return false;
        }
    }

}
