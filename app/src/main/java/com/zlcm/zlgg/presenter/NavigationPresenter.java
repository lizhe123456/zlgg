package com.zlcm.zlgg.presenter;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2018/1/12.
 * 类介绍：
 */

public class NavigationPresenter extends BasePresenterImpl<NavigationContract.View> implements NavigationContract.Presenter {

    DataManager dataManager;
    @Inject
    public NavigationPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getNavigation() {
        addSubscribe(dataManager.getNavigation()
                .compose(RxUtil.<ZL2Response<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleZL2())
                .subscribeWith(new CommonSubscriber<String>(mView){
                    @Override
                    public void onNext(String s) {
                        mView.showContent(s);
                        super.onNext(s);
                    }
                })
        );
    }
}
