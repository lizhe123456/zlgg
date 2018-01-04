package com.zlcm.zlgg.presenter.release.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;

import java.util.List;

/**
 * Created by lizhe on 2018/1/4.
 * 类介绍：
 */

public interface DeliveryInfoContract {

    interface View extends BaseView{
        void showContent(List<PeripheryDeviceBean.Device> list);

        void showMore(List<PeripheryDeviceBean.Device> list);

    }

    interface Presenter extends BasePresenter<View>{
        void getDeliveryInfo(String province, String city, String area, List<Integer> devices);

        void getMore();
    }
}
