package com.zlcm.zlgg.ui.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.presenter.user.UserInfoPresenter;
import com.zlcm.zlgg.presenter.user.contract.UserInfoContract;
import com.zlcm.zlgg.utils.BmpUtils;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.utils.SpUtil;
import com.zlcm.zlgg.view.RoundImageView;
import com.zlcm.zlgg.view.ZlCustomDialog;
import com.zlcm.zlgg.view.ZlToast;
import com.zlcm.zlgg.widgets.PhotoListActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：个人信息页面
 */

public class UserInfoActivity extends MvpActivity<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.real_name)
    TextView realName;
    @BindView(R.id.idCrad)
    TextView idCrad;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.store_auth)
    TextView storeAuth;
    @BindView(R.id.store_name)
    TextView storeName;
    @BindView(R.id.store_phone)
    TextView storePhone;
    @BindView(R.id.store_address)
    TextView storeAddress;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.iv_go3)
    ImageView ivGo3;
    @BindView(R.id.iv_go2)
    ImageView ivGo2;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.real_name_info)
    LinearLayout realNameInfo;
    @BindView(R.id.store_info)
    LinearLayout storeInfo;
    @BindView(R.id.name_auth_tv)
    TextView nameAuth;
    @BindView(R.id.iv_avatar)
    RoundImageView ivAvatar;

    private boolean isNameAuth = false;
    private boolean isStoreAuth = false;

    //认证信息展开收起状态
    private boolean isNameAuthS = true;
    private boolean isStoreAuthS = true;


    private static final int USERINfO = 3;

    @Override
    protected int setLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        title.setText(R.string.user_info);
        String avatarUrl =  SpUtil.getString(this,"avatar");
        String nickName =  SpUtil.getString(this,"nickName");
        String username = SpUtil.getString(this,"username");
        if (!TextUtils.isEmpty(avatarUrl) && !TextUtils.isEmpty(nickName)){
            GlideuUtil.loadImageView(this,avatarUrl,ivAvatar);
            this.nickName.setText(nickName);
        }else {
            if (username != null){
                mPresenter.getUserInfo(username);
            }
        }
    }

    @Override
    public void stateError() {

    }

    @Override
    public void showContent(UserInfoBean bean) {
        if (bean.getReal_name() != null && bean.getIdCrad() != null) {
            isNameAuth = true;
            nameAuth.setTextColor(getResources().getColor(R.color.tv_666));
            nameAuth.setText("已认证");
            ivGo2.setVisibility(View.GONE);
        }
        if (bean.getStorename() != null) {
            isStoreAuth = true;
            storeAuth.setTextColor(getResources().getColor(R.color.tv_666));
            storeAuth.setText("已认证");
            ivGo3.setVisibility(View.GONE);
        }
        if (isNameAuth) {
            realNameInfo.setVisibility(View.VISIBLE);
            realName.setText(bean.getReal_name());
            idCrad.setText(bean.getIdCrad());
            birthday.setText(bean.getBirthday());
            sex.setText(bean.getSex());
        } else {
            realNameInfo.setVisibility(View.GONE);
        }
        if (isStoreAuth) {
            storeInfo.setVisibility(View.VISIBLE);
            storeAddress.setText(bean.getAddress());
            storeName.setText(bean.getStorename());
            storePhone.setText(bean.getPhone());
        } else {
            storeInfo.setVisibility(View.GONE);
        }

        phone.setText(bean.getPhone());
        nickName.setText(bean.getNickname());

    }

    @Override
    public void upload() {
        ZlToast.showText(this,"上传成功");
    }

    @OnClick({R.id.avatar, R.id.btn_name_auth, R.id.btn_store_auth, R.id.btn_phone, R.id.btn_nick_name, R.id.img_lift})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.avatar:
                //到相机相册选择,返回时上传
                intent.setClass(this, PhotoListActivity.class);
                intent.putExtra("clipType",1);
                startActivityForResult(intent, USERINfO);
                break;
            case R.id.btn_name_auth:
                if (isNameAuth) {

                }else {
                    intent.setClass(this, NameAuthActivity.class);
                    startActivityForResult(intent, USERINfO);
                }
                break;
            case R.id.btn_store_auth:
                //跳转商家认证页面
                if (isStoreAuth){

                }else {
                    intent.setClass(this, StoreAuthActivity.class);
                    startActivityForResult(intent, USERINfO);
                }
                break;
            case R.id.btn_phone:
                //跳转更改手机号页面
                intent.setClass(this,UpdatePhoneActivity.class);
                startActivityForResult(intent,USERINfO);
                break;
            case R.id.btn_nick_name:
                //跳转昵称修改页面
                intent.setClass(this, NickNameActivity.class);
                intent.putExtra("nickName", nickName.getText().toString().trim());
                startActivityForResult(intent, USERINfO);
                break;
            case R.id.img_lift:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == USERINfO) {
            switch (resultCode) {
                case NickNameActivity.NICKNAME_RESULT:
                    String name = data.getStringExtra("text");
                    nickName.setText(name);
                    break;
                case Activity.RESULT_OK:
                    String bmp = data.getStringExtra("bitmap");
                    if (!TextUtils.isEmpty(bmp)) {
                        GlideuUtil.loadImageView(this,bmp,ivAvatar);
                        SpUtil.putString(this,"avatar",bmp);
                        SpUtil.putString(this,"avatar",nickName.getText().toString().trim());
                        File file = new File(bmp);
                        mPresenter.updateAvatar(file,nickName.getText().toString().trim());
                    }
                    break;
                case UpdatePhoneActivity.RESULT:

                    break;

            }
        }

    }

}
