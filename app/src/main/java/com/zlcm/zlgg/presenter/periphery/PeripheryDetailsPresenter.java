package com.zlcm.zlgg.presenter.periphery;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.PeripheryDetailsBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.periphery.contract.PeripheryDetailsContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2017/12/30.
 * 类介绍：
 */

public class PeripheryDetailsPresenter extends BasePresenterImpl<PeripheryDetailsContract.View>
        implements PeripheryDetailsContract.Presenter{

    DataManager dataManager;
    private int page;
    private int did;
    private static final int SIZE = 5;
    @Inject
    public PeripheryDetailsPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }


    @Override
    public void getPeripheryDetailsInfo(int did) {
        mView.loading("加载中...");
        this.did = did;
        addSubscribe(dataManager.fetchPeripheryDetailsInfo(this.did,page,SIZE,0)
                .compose(RxUtil.<ZLResponse<PeripheryDetailsBean>>rxSchedulerHelper())
                .compose(RxUtil.<PeripheryDetailsBean>handleZL())
                .subscribeWith(new CommonSubscriber<PeripheryDetailsBean>(mView){
                    @Override
                    public void onNext(PeripheryDetailsBean peripheryDetailsBean) {
                        mView.showContent(peripheryDetailsBean.getAdvert());
                        mView.showHead(peripheryDetailsBean.getCharging(),peripheryDetailsBean.getHot(),peripheryDetailsBean.getVisitorsFlowrate());
                        page++;
                        super.onNext(peripheryDetailsBean);
                    }
                })
        );
    }

    @Override
    public void getMore() {
        addSubscribe(dataManager.fetchPeripheryDetailsInfo(this.did,page,SIZE,1)
                .compose(RxUtil.<ZLResponse<PeripheryDetailsBean>>rxSchedulerHelper())
                .compose(RxUtil.<PeripheryDetailsBean>handleZL())
                .subscribeWith(new CommonSubscriber<PeripheryDetailsBean>(mView){
                    @Override
                    public void onNext(PeripheryDetailsBean peripheryDetailsBean) {
                        mView.showMore(peripheryDetailsBean.getAdvert());
                        page++;
                        super.onNext(peripheryDetailsBean);
                    }
                })
        );
    }
}
