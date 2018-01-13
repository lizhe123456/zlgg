package com.zlcm.zlgg.presenter.user;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.contract.StoreAuthContract;
import com.zlcm.zlgg.utils.RxUtil;
import javax.inject.Inject;

/**
 * Created by lizhe on 2018/1/11.
 * 类介绍：
 */

public class StoreAuthPresenter extends BasePresenterImpl<StoreAuthContract.View> implements StoreAuthContract.Presenter{

    DataManager dataManager;
    @Inject
    public StoreAuthPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void storeAuth(String name, String address, String phone, String image) {
        mView.loading("提交中...");
        addSubscribe(dataManager.fetchStoreAuth(name, address, phone, image)
                .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                .compose(RxUtil.handleZLState())
                .subscribeWith(new CommonSubscriber<ZLResponse>(mView){
                    @Override
                    public void onNext(ZLResponse response) {
                        super.onNext(response);
                        mView.showContent();
                    }
                })
        );
    }
}
