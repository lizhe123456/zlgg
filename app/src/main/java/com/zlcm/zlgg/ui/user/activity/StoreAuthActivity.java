package com.zlcm.zlgg.ui.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.presenter.user.StoreAuthPresenter;
import com.zlcm.zlgg.presenter.user.contract.StoreAuthContract;
import com.zlcm.zlgg.utils.Base64Utils;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.view.ZlToast;
import com.zlcm.zlgg.widgets.PhotoListActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：商家认证，需要审核
 */

public class StoreAuthActivity extends MvpActivity<StoreAuthPresenter> implements StoreAuthContract.View {

    private static final int NAMEAUTH_BMP = 7;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.etIdName)
    EditText etIdName;
    @BindView(R.id.etIdAddress)
    EditText etIdAddress;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.iv_positive)
    ImageView ivPositive;

    private String path;
    private String bmp;

    @Override
    protected int setLayout() {
        return R.layout.activity_store_auth;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        title.setText("商家认证");
    }

    @OnClick({R.id.img_lift,R.id.iv_positive,R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.iv_positive:
                Intent intent = new Intent();
                intent.setClass(this, PhotoListActivity.class);
                intent.putExtra("clipType",3);
                startActivityForResult(intent,NAMEAUTH_BMP);
                break;
            case R.id.btnSubmit:
                String storeName = etIdName.getText().toString().trim();
                if (TextUtils.isEmpty(storeName)){
                    ZlToast.showText(this,"请输入商店(公司)名");
                    return;
                }
                String storeAddress = etIdAddress.getText().toString().trim();
                if (TextUtils.isEmpty(storeAddress)){
                    ZlToast.showText(this,"请输入详细地址");
                    return;
                }
                String storePhone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(storePhone)){
                    ZlToast.showText(this,"请留个联系方式");
                    return;
                }
                if (TextUtils.isEmpty(bmp)){
                    ZlToast.showText(this,"请选择一张营业执照正面照片");
                    return;
                }
                mPresenter.storeAuth(storeName,storeAddress,storePhone,bmp);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NAMEAUTH_BMP){
            switch (resultCode){
                case Activity.RESULT_OK:
                    path = data.getStringExtra("bitmap");
                    if (!TextUtils.isEmpty(path)) {
                        GlideuUtil.loadImageView(this,path,ivPositive);
                        bmp = Base64Utils.Bitmap2StrByBase64(path);
                    }
                    break;
            }
        }
    }

    @Override
    public void stateError() {

    }

    @Override
    public void showContent() {
        //提交成功
        mActivity.finish();
    }
}
