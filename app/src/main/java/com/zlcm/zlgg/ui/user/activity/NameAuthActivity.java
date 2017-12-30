package com.zlcm.zlgg.ui.user.activity;

import android.view.View;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：实名认证页面
 */

public class NameAuthActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;

    @Override
    protected int setLayout() {
        return R.layout.activity_name_auth;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setData() {
        title.setText("实名认证");
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
