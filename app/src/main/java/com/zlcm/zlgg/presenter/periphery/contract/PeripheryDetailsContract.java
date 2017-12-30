package com.zlcm.zlgg.presenter.periphery.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.AdvertBean;

import java.util.List;

/**
 * Created by lizhe on 2017/12/30.
 * 类介绍：
 */

public interface PeripheryDetailsContract {

    interface View extends BaseView{

        void showContent(List<AdvertBean> advertBeen);

        void showMore(List<AdvertBean> advertBeen);

        void showHead(double charging, int hot, int visitorsFlowrate);
    }

    interface Presenter extends BasePresenter<View>{

        void getPeripheryDetailsInfo(int did);

        void getMore();
    }
}
