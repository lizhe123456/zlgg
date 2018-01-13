package com.zlcm.zlgg.presenter.user;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.AuthInfo;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.contract.NameAuthContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2018/1/8.
 * 类介绍：
 */

public class NameAuthPresenter extends BasePresenterImpl<NameAuthContract.View> implements NameAuthContract.Presenter{

    DataManager dataManager;

    @Inject
    public NameAuthPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void nameAuth(String name, String idCard, String front, String back) {
        mView.loading("验证中...");
        addSubscribe(dataManager.fetchAuthName(name, idCard, front, back)
                .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                .compose(RxUtil.handleZLState())
                .subscribeWith(new CommonSubscriber<ZLResponse>(mView){
                    @Override
                    public void onNext(ZLResponse response) {
                        super.onNext(response);
                        mView.authInfo();
                    }
                })

        );
    }
}
