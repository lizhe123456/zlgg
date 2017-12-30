package com.zlcm.zlgg.ui.hot.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import com.zlcm.zlgg.model.bean.AdvertBean;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.view.ZlToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/30.
 * 类介绍：
 */

public class AdvertDetailsActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.aimage)
    ImageView aimage;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.tv_hot)
    TextView hot;

    private AdvertBean advertBean;

    @Override
    protected int setLayout() {
        return R.layout.activity_advert_details;
    }

    @Override
    protected void init() {
        title.setText("广告详情");
    }

    @Override
    protected void setData() {
        advertBean = (AdvertBean) getIntent().getSerializableExtra("advert");
        if (advertBean != null) {
            GlideuUtil.loadImageView(this, advertBean.getAdvertImg(), aimage);
            tvPhone.setText(advertBean.getPhone());
            tvName.setText(advertBean.getNickname());
            tvTime.setText(advertBean.getStartTime());
            tvAddress.setText(advertBean.getAddress());
            desc.setText(advertBean.getTextInfo());
            hot.setText(advertBean.getHits()+"");
        }else {
            ZlToast.showText(this,"加载失败");
        }
    }



    @OnClick({R.id.img_lift, R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.tv_phone:
                if (advertBean != null) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + advertBean.getPhone()));
                    startActivity(dialIntent);
                }
                break;
        }
    }
}
