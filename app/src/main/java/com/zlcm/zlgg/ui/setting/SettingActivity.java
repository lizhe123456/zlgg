package com.zlcm.zlgg.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zlcm.zlgg.R;
import com.zlcm.zlgg.app.Constants;
import com.zlcm.zlgg.base.BaseActivity;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.NewVersionInfoBean;
import com.zlcm.zlgg.presenter.setting.SettingPresenter;
import com.zlcm.zlgg.presenter.setting.contract.SettingContract;
import com.zlcm.zlgg.ui.setting.activity.FeedBackActivity;
import com.zlcm.zlgg.utils.DataCleanManager;
import com.zlcm.zlgg.utils.PackageUtil;
import com.zlcm.zlgg.utils.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：lizhe
 * time： 2017-12-18
 * 不积跬步,无以至千里.不积小流,无以成江河
 * 类介绍：设置页面
 */

public class SettingActivity extends MvpActivity<SettingPresenter> implements SettingContract.View{

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cache_size)
    TextView cacheSize;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.exit_login)
    TextView exitLogin;

    private static final int SETTING = 3;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }


    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setData() {
        title.setText(R.string.setting);
        version.setText(PackageUtil.getVersionName(mActivity));
        try {
            cacheSize.setText(DataCleanManager.getTotalCacheSize(mActivity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.img_lift, R.id.clean_out_cache, R.id.contact_us, R.id.version_update, R.id.feedback, R.id.about_us,R.id.exit_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_lift:
                finish();
                break;
            case R.id.clean_out_cache:
                DataCleanManager.cleanApplicationData(mActivity, Constants.PATH_CACHE);
                try {
                    String size = DataCleanManager.getTotalCacheSize(mActivity);
                    Toast.makeText(mActivity, size, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.contact_us:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17688943972"));
                startActivity(dialIntent);
                break;
            case R.id.version_update:
                mPresenter.getNewVersion(PackageUtil.getVersionName(mActivity));
                break;
            case R.id.feedback:
                Intent intent = new Intent();
                intent.setClass(mActivity, FeedBackActivity.class);
                startActivityForResult(intent,SETTING);
                break;
            case R.id.about_us:
                break;
            case R.id.exit_login:
                mPresenter.exitLogin();
                break;
        }
    }

    @Override
    public void stateError() {

    }

    @Override
    public void showNewVersion(NewVersionInfoBean bean) {

    }

    @Override
    public void exit() {
        SpUtil.putString(this,"username",null);
        SpUtil.putString(this,"loginId",null);
        SpUtil.putString(this,"token",null);
        finish();
    }

    @Override
    public void back() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTING){
            if (resultCode == FeedBackActivity.FEEDBACK){
                String desc = data.getStringExtra("desc");
                String phone = data.getStringExtra("phone");
                mPresenter.feedback(desc,phone);
            }
        }
    }
}
