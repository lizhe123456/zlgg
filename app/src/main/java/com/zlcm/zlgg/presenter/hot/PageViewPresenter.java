package com.zlcm.zlgg.presenter.hot;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.hot.contract.PageViewContract;
import com.zlcm.zlgg.utils.RxUtil;
import javax.inject.Inject;

/**
 * Created by lizhe on 2018/1/23.
 * 类介绍：
 */

public class PageViewPresenter extends BasePresenterImpl<PageViewContract.View> implements PageViewContract.Presenter{

    DataManager dataManager;

    @Inject
    public PageViewPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void submitPageView(int aid) {
        addSubscribe(dataManager.addPageView(aid)
                .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                .compose(RxUtil.handleZLState())
                .subscribeWith(new CommonSubscriber<ZLResponse>(mView){
                    @Override
                    public void onNext(ZLResponse response) {

                    }
                })
        );
    }
}
