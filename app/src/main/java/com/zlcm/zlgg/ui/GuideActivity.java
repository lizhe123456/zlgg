package com.zlcm.zlgg.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.App;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.lib.LocationTask;
import com.zlcm.zlgg.presenter.NavigationContract;
import com.zlcm.zlgg.presenter.NavigationPresenter;
import com.zlcm.zlgg.utils.SystemUtil;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;


/**
 * Created by lizhe on 2017/12/26.
 * 类介绍：
 */

public class GuideActivity extends MvpActivity<NavigationPresenter> implements NavigationContract.View {

    @BindView(R.id.navigation)
    ImageView navigation;
    @BindView(R.id.text)
    TextView text;
    private Handler handler;
    private int recLen = 4;
    Timer timer = new Timer();
    private AlertDialog dialog;

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            Intent intent = new Intent(GuideActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            GuideActivity.this.finish();
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {  // UI thread
                @Override
                public void run() {
                    recLen--;
                    text.setText("跳过 "+recLen +"s");
                    if(recLen < 0){
                        timer.cancel();
                        text.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    @Override
    protected int setLayout() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_guide;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isNetwork();
    }

    protected void setData() {
        isNetwork();
    }

    public void isNetwork(){
        //检查是否有网络
        if (SystemUtil.isNetworkConnected(this)) {
            mPresenter.getNavigation();
            LocationTask.getInstance(this).startLocate();
            handler = new Handler();
        }else {
            navigation.setImageResource(R.drawable.navigation);
            if (dialog == null) {
                dialog = new AlertDialog.Builder(this)
                        .setMessage(R.string.no_network_connected)
                        .setCancelable(false)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                App.getInstance().exitApp();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.cancel();
                                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                                startActivity(intent);
                            }
                        }).show();
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextSize(16);
                dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#8C8C8C"));
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextSize(16);
                dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#1DA6DD"));
            }else {
                dialog.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        timer.cancel();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    public void stateError() {
        navigation.setImageResource(R.drawable.navigation);
        timer.schedule(task, 1000, 1000);
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void showContent(String img) {
        Glide.with(this).load(img.trim()).error(R.drawable.navigation).diskCacheStrategy(DiskCacheStrategy.ALL).into(navigation);
        timer.schedule(task, 1000, 1000);
        handler.postDelayed(runnable, 3000);
    }

}
