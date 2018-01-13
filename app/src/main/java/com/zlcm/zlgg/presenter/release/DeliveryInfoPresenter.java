package com.zlcm.zlgg.presenter.release;

import com.google.gson.Gson;
import com.zlcm.zlgg.base.BasePresenterImpl;
import com.zlcm.zlgg.base.CommonSubscriber;
import com.zlcm.zlgg.model.DataManager;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import com.zlcm.zlgg.presenter.release.contract.DeliveryInfoContract;
import com.zlcm.zlgg.utils.RxUtil;
import java.util.List;
import javax.inject.Inject;
import okhttp3.RequestBody;

/**
 * Created by lizhe on 2018/1/4.
 * 类介绍：
 */

public class DeliveryInfoPresenter extends BasePresenterImpl<DeliveryInfoContract.View> implements DeliveryInfoContract.Presenter {

    DataManager dataManager;
    private String province;
    private String city;
    private String area;
    private List<Integer> devices;
    private int page = 0;

    @Inject
    public DeliveryInfoPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void getDeliveryInfo(String province, String city, String area, List<Integer> devices) {
        page = 0;
        mView.loading("加载中...");
        this.province = province;
        this.city = city;
        this.area = area;
        this.devices = devices;
        Gson gson = new Gson();
        String body = gson.toJson(devices);

        addSubscribe(dataManager.fetchDeliveryDeviceList(province, city, area, body, page)
                .compose(RxUtil.<ZLResponse<PeripheryDeviceBean>>rxSchedulerHelper())
                .compose(RxUtil.<PeripheryDeviceBean>handleZL())
                .subscribeWith(new CommonSubscriber<PeripheryDeviceBean>(mView){
                    @Override
                    public void onNext(PeripheryDeviceBean peripheryDeviceBean) {
                        mView.showContent(peripheryDeviceBean.getList());
                        page++;
                        super.onNext(peripheryDeviceBean);
                    }
                })
        );
    }

    @Override
    public void getMore() {
        Gson gson = new Gson();
        String body = gson.toJson(devices);
        addSubscribe(dataManager.fetchDeliveryDeviceList(province, city, area, body, page)
                .compose(RxUtil.<ZLResponse<PeripheryDeviceBean>>rxSchedulerHelper())
                .compose(RxUtil.<PeripheryDeviceBean>handleZL())
                .subscribeWith(new CommonSubscriber<PeripheryDeviceBean>(mView){
                    @Override
                    public void onNext(PeripheryDeviceBean peripheryDeviceBean) {
                        mView.showMore(peripheryDeviceBean.getList());
                        page++;
                        super.onNext(peripheryDeviceBean);
                    }
                })
        );
    }
}
