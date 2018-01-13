package com.zlcm.zlgg.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import com.zlcm.zlgg.R;
import com.zlcm.zlgg.base.MvpActivity;
import com.zlcm.zlgg.model.bean.NewVersionInfoBean;
import com.zlcm.zlgg.presenter.setting.SettingPresenter;
import com.zlcm.zlgg.presenter.setting.contract.SettingContract;
import com.zlcm.zlgg.ui.setting.activity.FeedBackActivity;
import com.zlcm.zlgg.utils.DataCleanManager;
import com.zlcm.zlgg.utils.PackageUtil;
import com.zlcm.zlgg.utils.SpUtil;
import com.zlcm.zlgg.view.ZlToast;
import butterknife.BindView;
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
                try {
                    if (!cacheSize.getText().toString().trim().equals("0K")) {
                        DataCleanManager.clearAllCache(mActivity);
                        cacheSize.setText(DataCleanManager.getTotalCacheSize(mActivity));
                        ZlToast.showText(mActivity, "清理成功");
                    }else {
                        ZlToast.showText(mActivity, "已经很干净了");
                    }
                } catch (Exception e) {
                    ZlToast.showText(mActivity, "清理失败");
                }
                break;
            case R.id.contact_us:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "17688943972"));
                startActivity(dialIntent);
                break;
            case R.id.version_update:
                mPresenter.getNewVersion();
                break;
            case R.id.feedback:
                Intent intent = new Intent();
                intent.setClass(mActivity, FeedBackActivity.class);
                startActivity(intent);
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
    public void showNewVersion(NewVersionInfoBean.AppVersion bean) {
        if (bean.getCode().equals(PackageUtil.getVersionName(mActivity))){
            ZlToast.showText(this,"已是最新版本");
        }else {
            //弹出版本更新对话框，非强制

        }

    }

    @Override
    public void exit() {
        SpUtil.putString(this,"username",null);
        SpUtil.putString(this,"loginId",null);
        SpUtil.putString(this,"token",null);
        finish();
    }

}
