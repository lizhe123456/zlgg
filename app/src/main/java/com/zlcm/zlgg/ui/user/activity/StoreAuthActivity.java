package com.zlcm.zlgg.ui.user.activity;

import android.view.View;
import android.widget.TextView;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：商家认证，需要审核
 */

public class StoreAuthActivity extends BaseActivity{

    @BindView(R.id.title)
    TextView title;

    @Override
    protected int setLayout() {
        return R.layout.activity_store_auth;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setData() {
        title.setText("商家认证");
    }

    @OnClick({R.id.img_lift})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.img_lift:
                finish();
                break;
        }
    }
}
