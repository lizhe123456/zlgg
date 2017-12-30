package com.zlcm.zlgg.presenter.periphery;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.periphery.contract.PeripheryContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2017/12/29.
 * 类介绍：
 */

public class PeripheryPresenter extends BasePresenterImpl<PeripheryContract.View> implements PeripheryContract.Presenter {

    DataManager dataManager;
    private int page;


    @Inject
    public PeripheryPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getPeriphery(double lng, double lat) {
        if (page == 0) {
            mView.loading("加载中...");
        }
        addSubscribe(dataManager.fetchDeviceBList(lng, lat,page,SIZE)
                .compose(RxUtil.<ZLResponse<PeripheryDeviceBean>>rxSchedulerHelper())
                .compose(RxUtil.<PeripheryDeviceBean>handleZL())
                .subscribeWith(new CommonSubscriber<PeripheryDeviceBean>(mView){
                    @Override
                    public void onNext(PeripheryDeviceBean peripheryDeviceBean) {
                        if (page == 0) {
                            mView.showContent(peripheryDeviceBean.getList());
                        }else {
                            mView.showMore(peripheryDeviceBean.getList());
                        }
                        page++;
                        super.onNext(peripheryDeviceBean);
                    }
                })
        );
    }
}
