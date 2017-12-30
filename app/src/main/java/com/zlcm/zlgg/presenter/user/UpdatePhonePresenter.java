package com.zlcm.zlgg.presenter.user;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.user.contract.UpdatePhoneContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：
 */

public class UpdatePhonePresenter extends BasePresenterImpl<UpdatePhoneContract.View>
        implements UpdatePhoneContract.Presenter{
    DataManager dataManager;

    @Inject
    public UpdatePhonePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getUpdateCode(String phone) {
        addSubscribe(dataManager.fetchMobileCodeInfo(phone)
                .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                .compose(RxUtil.handleZLState())
                .subscribeWith(new CommonSubscriber<ZLResponse>(mView) {
                    @Override
                    public void onNext(ZLResponse zlResponse) {
                        super.onNext(zlResponse);
                        mView.codeState(zlResponse);
                    }
                })
        );
    }

    @Override
    public void provingPhone(String mobile, String code) {
        mView.loading("正在更换...");
        addSubscribe(dataManager.fetchProvingPhone(mobile,code)
                .compose(RxUtil.<ZLResponse>rxSchedulerHelper())
                .compose(RxUtil.handleZLState())
                .subscribeWith(new CommonSubscriber<ZLResponse>(mView){
                    @Override
                    public void onNext(ZLResponse response) {
                        super.onNext(response);
                        mView.provingSate(response);
                    }
                })
        );
    }
}
