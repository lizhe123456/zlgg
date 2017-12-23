package com.zlcm.zlgg.ui.setting.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：
 */

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.phone)
    EditText phone;

    @Override
    protected int setLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setData() {
        title.setText(R.string.feedback);
    }


    @OnClick({R.id.img_lift, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.submit:
                break;
        }
    }
}