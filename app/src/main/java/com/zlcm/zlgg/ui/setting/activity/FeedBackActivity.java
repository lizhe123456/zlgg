package com.zlcm.zlgg.ui.setting.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.presenter.setting.FeedBackPresenter;
import com.zlcm.zlgg.presenter.setting.contract.FeedBackContract;
import com.zlcm.zlgg.view.ZlToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：
 */

public class FeedBackActivity extends MvpActivity<FeedBackPresenter> implements FeedBackContract.View {

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
    protected void initInject() {
        getActivityComponent().inject(this);
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
                if (!TextUtils.isEmpty(etDesc.getText().toString())) {
                    String desc = etDesc.getText().toString().trim();
                    String ph = phone.getText().toString().trim() == null ? "" : phone.getText().toString().trim();
                    mPresenter.feedback(desc,ph);
                }else {
                    ZlToast.showText(this,"写点东西呗...");
                }
                break;
        }
    }

    @Override
    public void back() {
        ZlToast.showText(this,"提交成功");
        finish();
    }

    @Override
    public void stateError() {

    }
}
