package com.zlcm.zlgg.presenter.release.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.ChargInfoBean;

import java.util.List;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public interface OrderContract {

    interface View extends BaseView{
        void showContent(List<ChargInfoBean> list);

        void showMore(List<ChargInfoBean> list);
    }

    interface Presenter extends BasePresenter<View>{
        void getOrderList();

        void getMore();
    }

}
