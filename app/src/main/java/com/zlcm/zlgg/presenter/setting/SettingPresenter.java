package com.zlcm.zlgg.presenter.setting;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.NewVersionInfoBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.setting.contract.SettingContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：设置页面
 */

public class SettingPresenter extends BasePresenterImpl<SettingContract.View>
        implements SettingContract.Presenter{

    DataManager dataManager;
    @Inject
    public SettingPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void exitLogin() {
        mView.loading("正在退出...");
        addSubscribe(dataManager.fetchExitLogin()
                .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                .compose(RxUtil.handleZLState())
                .subscribeWith(new CommonSubscriber<ZLResponse>(mView){
                    @Override
                    public void onNext(ZLResponse response) {
                        mView.exit();
                        super.onNext(response);
                    }
                })
        );
    }

    @Override
    public void getNewVersion() {
        mView.loading("正在检查...");
        addSubscribe(dataManager.fetchNewVersion()
                .compose(RxUtil.<ZLResponse<NewVersionInfoBean>>rxSchedulerHelper())
                .compose(RxUtil.<NewVersionInfoBean>handleZL())
                .subscribeWith(new CommonSubscriber<NewVersionInfoBean>(mView){
                    @Override
                    public void onNext(NewVersionInfoBean newVersionInfoBean) {
                        mView.showNewVersion(newVersionInfoBean.getVersion());
                        super.onNext(newVersionInfoBean);
                    }
                })
        );
    }


}
