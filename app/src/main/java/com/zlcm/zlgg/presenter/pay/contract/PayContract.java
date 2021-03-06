package com.zlcm.zlgg.presenter.pay.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.PayInfo;

import java.util.Map;

/**
 * Created by lizhe on 2018/1/7.
 * 类介绍：
 */

public interface PayContract {

    interface View extends BaseView{
        void paySate(Map<String,String> pay);
    }

    interface Presenter extends BasePresenter<View>{
        void pay(int oid,int type);
    }

}
