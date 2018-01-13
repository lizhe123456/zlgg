package com.zlcm.zlgg.presenter;

import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.ChargingBean;
import com.zlcm.zlgg.model.bean.HomePageBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.utils.RxUtil;
import javax.inject.Inject;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：首页数据
 */

public class HomePagePresenter extends BasePresenterImpl<HomePageContract.View>
        implements HomePageContract.Presenter{

    DataManager dataManager;
    private boolean isFrist = true;

    @Inject
    public HomePagePresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getHomePage(double longitude, double latitude) {
        if (isFrist) {
            mView.loading("加载中...");
        }
        addSubscribe(dataManager.fetchHomePage(longitude,latitude,isFrist == true ? 0 : 1)
                .compose(RxUtil.<ZLResponse<HomePageBean>>rxSchedulerHelper())
                .compose(RxUtil.<HomePageBean>handleZL())
                .subscribeWith(new CommonSubscriber<HomePageBean>(mView){
                    @Override
                    public void onNext(HomePageBean homePageBean) {
                        super.onNext(homePageBean);
                        mView.showContent(homePageBean);
                    }
                })
        );
        isFrist = false;
    }

    @Override
    public void getHomeDeviceList(double longitude, double latitude) {

    }

    @Override
    public void getChargingInfo(int did) {
        mView.loading("加载中..");
        addSubscribe(dataManager.fetchChargingInfo(did)
                .compose(RxUtil.<ZLResponse<ChargingBean>>rxSchedulerHelper())
                .compose(RxUtil.<ChargingBean>handleZL())
                .subscribeWith(new CommonSubscriber<ChargingBean>(mView){
                    @Override
                    public void onNext(ChargingBean chargingBean) {
                        mView.showChargingInfo(chargingBean);
                        super.onNext(chargingBean);
                    }
                })
        );
    }

}
