package com.zlcm.zlgg.presenter.release;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.OrderListBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.release.contract.OrderContract;
import com.zlcm.zlgg.utils.RxUtil;

import javax.inject.Inject;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public class OrderPresenter extends BasePresenterImpl<OrderContract.View> implements OrderContract.Presenter {

    DataManager dataManager;
    private int page;

    @Inject
    public OrderPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getOrderList() {
        mView.loading("加载中...");
        page = 0;
        addSubscribe(dataManager.fetchOrderListInfo(SIZE,page)
                .compose(RxUtil.<ZLResponse<OrderListBean>>rxSchedulerHelper())
                .compose(RxUtil.<OrderListBean>handleZL())
                .subscribeWith(new CommonSubscriber<OrderListBean>(mView){
                    @Override
                    public void onNext(OrderListBean orderListBean) {
                        mView.showContent(orderListBean.getList());
                        page++;
                        super.onNext(orderListBean);
                    }
                })
        );
    }

    @Override
    public void getMore() {
        addSubscribe(dataManager.fetchOrderListInfo(SIZE,page)
                .compose(RxUtil.<ZLResponse<OrderListBean>>rxSchedulerHelper())
                .compose(RxUtil.<OrderListBean>handleZL())
                .subscribeWith(new CommonSubscriber<OrderListBean>(mView){
                    @Override
                    public void onNext(OrderListBean orderListBean) {
                        super.onNext(orderListBean);
                        mView.showMore(orderListBean.getList());
                        page++;
                    }
                })
        );
    }
}
