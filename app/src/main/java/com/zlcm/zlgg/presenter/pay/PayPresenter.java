package com.zlcm.zlgg.presenter.pay;

import com.google.gson.Gson;
import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.PayInfo;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.pay.contract.PayContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public class PayPresenter extends BasePresenterImpl<PayContract.View> implements PayContract.Presenter {

    DataManager dataManager;
    @Inject
    public PayPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void pay(int oid,int type) {
        mView.loading("支付中...");
        addSubscribe(dataManager.fetchPayInfo(oid,type)
                .compose(RxUtil.<ZL2Response<String>>rxSchedulerHelper())
                .compose(RxUtil.<String>handleZL2())
                .subscribeWith(new CommonSubscriber<String>(mView){
                    @Override
                    public void onNext(String json) {
                        super.onNext(json);
                        Gson gson = new Gson();
                        PayInfo payInfo = gson.fromJson(json, PayInfo.class);
                        mView.paySate(payInfo);
                    }
                })
        );
    }
}
