package com.zlcm.zlgg.presenter;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.ChargingBean;
import com.zlcm.zlgg.model.bean.HomePageBean;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：首页
 */

public interface HomePageContract {

    interface View extends BaseView{

        void showContent(HomePageBean homePageBean);

        void showChargingInfo(ChargingBean bean);
    }

    interface Presenter extends BasePresenter<View>{

        void getHomePage(double longitude, double latitude, int type);

        void getHomeDeviceList(double longitude, double latitude);

        void getChargingInfo(int did);
    }
}
