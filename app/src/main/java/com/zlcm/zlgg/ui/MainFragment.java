package com.zlcm.zlgg.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseFrament;
import com.zlcm.zlgg.ui.user.LoginActivity;
import com.zlcm.zlgg.utils.SpUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class MainFragment extends BaseFrament{

    @BindView(R.id.img_lift)
    ImageView imgLift;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.img_right1)
    ImageView imgRight1;
    @BindView(R.id.img_right2)
    ImageView imgRight2;
    @BindView(R.id.tv_btn_login)
    TextView tvBtnLogin;
    @BindView(R.id.rl_no_login)
    RelativeLayout rlNoLogin;
    @BindView(R.id.map)
    MapView mMapView;

    private AMap aMap;
    private boolean isLogin = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        imgLift.setImageResource(R.drawable.personal);
        title.setText(R.string.title_name);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
    }

    @Override
    protected void setData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        token(SpUtil.getString(getContext(),"token"));
    }

    @OnClick({R.id.img_lift, R.id.img_right1, R.id.img_right2, R.id.tv_btn_login,R.id.location, R.id.tv_release, R.id.customer_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                if (isLogin) {
                    ((MainActivity) getActivity()).openDrawerLayout();
                } else {
                    toLogin();
                }
                break;
            case R.id.img_right1:
                break;
            case R.id.img_right2:
                break;
            case R.id.tv_btn_login:
                toLogin();
                break;
            case R.id.location:
                break;
            case R.id.tv_release:
                if (isLogin){

                }else {
                    toLogin();
                }
                break;
            case R.id.customer_service:
                break;
        }
    }

    private void toLogin() {
        Intent intent = new Intent();
        intent.setClass(getContext(), LoginActivity.class);
        startActivity(intent);
    }


    public void token(String token) {
        if (TextUtils.isEmpty(token)) {
            isLogin = false;
            rlNoLogin.setVisibility(View.VISIBLE);
        } else {
            isLogin = true;
            rlNoLogin.setVisibility(View.GONE);
        }
    }

}
