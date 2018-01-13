package com.zlcm.zlgg.ui.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.BaseActivity;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.AuthInfo;
import com.zlcm.zlgg.presenter.user.NameAuthPresenter;
import com.zlcm.zlgg.presenter.user.contract.NameAuthContract;
import com.zlcm.zlgg.utils.Base64Utils;
import com.zlcm.zlgg.utils.GlideuUtil;
import com.zlcm.zlgg.view.ZlToast;
import com.zlcm.zlgg.widgets.PhotoListActivity;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lizhe on 2017/12/23.
 * 类介绍：实名认证页面
 */

public class NameAuthActivity extends MvpActivity<NameAuthPresenter> implements NameAuthContract.View {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.etIdName)
    EditText etIdName;
    @BindView(R.id.etIdNum)
    EditText etIdNum;
    @BindView(R.id.iv_positive)
    ImageView ivPositive;
    @BindView(R.id.iv_opposite)
    ImageView ivOpposite;
    @BindView(R.id.btnSubmit)
    TextView btnSubmit;

    private boolean isSubmit;
    //身份证正面
    private String front;
    private String frontPath;
    //身份证反面
    private String back;
    private String backPath;

    private int NAMEAUTH_FRONT = 6;
    private int NAMEAUTH_BACK = 7;

    @Override
    protected int setLayout() {
        return R.layout.activity_name_auth;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        title.setText("实名认证");
    }


    @OnClick({R.id.img_lift, R.id.iv_positive, R.id.iv_opposite, R.id.btnSubmit})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.iv_positive:
                intent.setClass(this, PhotoListActivity.class);
                intent.putExtra("clipType",3);
                startActivityForResult(intent,NAMEAUTH_FRONT);
                break;
            case R.id.iv_opposite:
                intent.setClass(this, PhotoListActivity.class);
                intent.putExtra("clipType",3);
                startActivityForResult(intent,NAMEAUTH_BACK);
                break;
            case R.id.btnSubmit:
                String name = etIdName.getText().toString().trim();
                if (TextUtils.isEmpty(name)){
                    ZlToast.showText(this,"请输入姓名");
                    return;
                }
                String idCrad = etIdNum.getText().toString().trim();
                if (TextUtils.isEmpty(idCrad)){
                    ZlToast.showText(this,"请输入身份证号码");
                    return;
                }
                if (TextUtils.isEmpty(front)){
                    ZlToast.showText(this,"请选择身份证正面");
                }
                if (TextUtils.isEmpty(back)){
                    ZlToast.showText(this,"请选择身份证背面");
                    return;
                }
                mPresenter.nameAuth(name,idCrad,front,back);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NAMEAUTH_FRONT){
            switch (resultCode){
                case Activity.RESULT_OK:
                    frontPath = data.getStringExtra("bitmap");
                    if (!TextUtils.isEmpty(frontPath)) {
                        GlideuUtil.loadImageView(this,frontPath,ivPositive);
                        front = Base64Utils.Bitmap2StrByBase64(frontPath);
                    }
                    break;
            }
        }else if (requestCode == NAMEAUTH_BACK){
            switch (resultCode){
                case Activity.RESULT_OK:
                    backPath = data.getStringExtra("bitmap");
                    if (!TextUtils.isEmpty(backPath)) {
                        GlideuUtil.loadImageView(this,backPath,ivOpposite);
                        back = Base64Utils.Bitmap2StrByBase64(backPath);
                    }
                    break;
            }
        }
    }

    @Override
    public void stateError() {

    }

    @Override
    public void authInfo() {
        mActivity.finish();
    }
}
