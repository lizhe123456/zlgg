package com.zlcm.zlgg.ui.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.presenter.user.UserInfoPresenter;
import com.zlcm.zlgg.presenter.user.contract.UserInfoContract;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.utils.SpUtil;
import com.zlcm.zlgg.view.RoundImageView;
import com.zlcm.zlgg.view.ZlToast;
import com.zlcm.zlgg.widgets.PhotoListActivity;
import java.io.File;
import butterknife.BindView;
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
    private boolean isNameAuthS = false;
    private boolean isStoreAuthS = false;


    private static final int USERINfO = 3;
    private String bmp;
    private String name;

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


    }

    @Override
    protected void onResume() {
        super.onResume();
        String username = SpUtil.getString(this,"username");
        if (!TextUtils.isEmpty(username)) {
            mPresenter.getUserInfo(username);
        }
    }

    @Override
    public void stateError() {

    }

    @Override
    public void showContent(UserInfoBean bean) {
        if (!TextUtils.isEmpty(bean.getReal_name())&& !TextUtils.isEmpty(bean.getIdCrad())) {
            isNameAuth = true;
            nameAuth.setTextColor(getResources().getColor(R.color.tv_666));
            nameAuth.setText("已认证");
            ivGo2.setVisibility(View.INVISIBLE);
        }else {
            isNameAuth = false;
            nameAuth.setText("未认证");
            ivGo2.setVisibility(View.VISIBLE);
        }
        if (bean.getStorename() != null) {
            if (bean.getStoreState() == 0){
                isStoreAuth = true;
                storeAuth.setText("审核中");
                ivGo3.setVisibility(View.INVISIBLE);
            }else if (bean.getStoreState() == 1) {
                isStoreAuth = true;
                storeAuth.setTextColor(getResources().getColor(R.color.tv_666));
                storeAuth.setText("已认证");
                ivGo3.setVisibility(View.INVISIBLE);
            }else {
                isStoreAuth = false;
                storeAuth.setText("审核失败");
                ivGo3.setVisibility(View.VISIBLE);
            }
        }else {
            isStoreAuth = false;
            storeAuth.setText("未认证");
            ivGo3.setVisibility(View.VISIBLE);
        }
        if (isNameAuth) {
//            realNameInfo.setVisibility(View.VISIBLE);
            realName.setText(bean.getReal_name());
            idCrad.setText(bean.getIdCrad());
            birthday.setText(bean.getBirthday());
            sex.setText(bean.getSex());
        }
        if (isStoreAuth) {
//            storeInfo.setVisibility(View.VISIBLE);
            storeAddress.setText(bean.getAddress());
            storeName.setText(bean.getStorename());
            storePhone.setText(bean.getPhone());
        }
        String avatarUrl =  SpUtil.getString(this,"avatar");
        if (!TextUtils.isEmpty(avatarUrl)){
            GlideuUtil.loadImageView(this,avatarUrl,ivAvatar);
        }

        nickName.setText(bean.getNickname());
        phone.setText(bean.getPhone());
    }

    @Override
    public void upload() {
        ZlToast.showText(this,"上传成功");
        GlideuUtil.loadImageView(this,bmp,ivAvatar);
        SpUtil.putString(this,"avatar",bmp);
    }

    @Override
    public void setState() {
        ZlToast.showText(this,"修改成功");
        nickName.setText(name);
        SpUtil.putString(this,"nickName",name);
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
                    if (isNameAuthS){
                        realNameInfo.setVisibility(View.GONE);
                        isNameAuthS = false;
                    }else {
                        realNameInfo.setVisibility(View.VISIBLE);
                        isNameAuthS = true;
                    }
                }else {
                    intent.setClass(this, NameAuthActivity.class);
                    startActivityForResult(intent, USERINfO);
                }
                break;
            case R.id.btn_store_auth:
                //跳转商家认证页面
                if (isStoreAuth){
                    if (isStoreAuthS){
                        storeInfo.setVisibility(View.GONE);
                        isStoreAuthS = false;
                    }else {
                        storeInfo.setVisibility(View.VISIBLE);
                        isStoreAuthS = true;
                    }
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
                    name = data.getStringExtra("text");
                    if (!TextUtils.isEmpty(name)) {
                        mPresenter.setNickName(name);
                    }
                    break;
                case Activity.RESULT_OK:
                    bmp = data.getStringExtra("bitmap");
                    if (!TextUtils.isEmpty(bmp)) {
                        File file = new File(bmp);
                        mPresenter.updateAvatar(file);
                    }
                    break;
                case UpdatePhoneActivity.RESULT:
                    break;
            }
        }
    }

}
