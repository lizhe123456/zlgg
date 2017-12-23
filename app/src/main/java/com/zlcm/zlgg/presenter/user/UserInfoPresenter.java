package com.zlcm.zlgg.presenter.user;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.contract.UserInfoContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2017/12/22.
 * 类介绍：用户信息presenter
 */

public class UserInfoPresenter extends BasePresenterImpl<UserInfoContract.View>
        implements UserInfoContract.Presenter{

    DataManager dataManager;

    @Inject
    public UserInfoPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void updateAvatar() {

    }

    @Override
    public void authen() {

    }

    @Override
    public void getUserInfo(String username) {
        mView.loading("加载中");
        addSubscribe(dataManager.fetchgetUserInfo(username)
                    .compose(RxUtil.<ZLResponse<UserInfoBean>>rxSchedulerHelper())
                    .compose(RxUtil.<UserInfoBean>handleZL())
                    .subscribeWith(new CommonSubscriber<UserInfoBean>(mView){

                    })
        );
    }
}
