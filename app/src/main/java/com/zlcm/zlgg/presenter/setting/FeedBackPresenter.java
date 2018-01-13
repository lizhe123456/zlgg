package com.zlcm.zlgg.presenter.setting;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.setting.contract.FeedBackContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2018/1/13.
 * 类介绍：
 */

public class FeedBackPresenter extends BasePresenterImpl<FeedBackContract.View> implements FeedBackContract.Presenter{

    DataManager dataManager;
    @Inject
    public FeedBackPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void feedback(String desc, String phone) {
        mView.loading("提交中...");
        addSubscribe(dataManager.fetchUploadFeed(desc,phone)
                .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                .compose(RxUtil.handleZLState())
                .subscribeWith(new CommonSubscriber<ZLResponse>(mView){
                    @Override
                    public void onNext(ZLResponse response) {
                        super.onNext(response);
                        mView.back();
                    }
                })
        );
    }
}
