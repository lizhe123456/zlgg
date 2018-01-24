package com.zlcm.zlgg.ui;

import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.view.WindowManager;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import com.zlcm.zlgg.widgets.ProgressWebview;

import butterknife.BindView;

/**
 * Created by lizhe on 2018/1/23.
 * 类介绍：腾讯tbs Web
 */

public class TbsWebActivity extends BaseActivity {

    @BindView(R.id.forum_context)
    ProgressWebview forumContext;



    @Override
    protected int setLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void init() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void setData() {
        String url = getIntent().getStringExtra("url");
        if (!TextUtils.isEmpty(url)){
            forumContext.loadUrl(url);
        }
    }
}
