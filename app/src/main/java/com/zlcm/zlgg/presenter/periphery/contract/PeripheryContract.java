package com.zlcm.zlgg.presenter.periphery.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;

import java.util.List;

/**
 * Created by lizhe on 2017/12/29.
 * 类介绍：
 */

public interface PeripheryContract {

    interface View extends BaseView{
        void showContent(List<PeripheryDeviceBean.Device> list);

        void showMore(List<PeripheryDeviceBean.Device> list);
    }

    interface Presenter extends BasePresenter<View>{
        void getPeriphery(double lng, double lat);
    }
}
