package com.zlcm.zlgg.presenter.user.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.AuthInfo;

/**
 * Created by lizhe on 2018/1/8.
 * 类介绍：
 */

public interface NameAuthContract {

    interface View extends BaseView{
        void authInfo();
    }

    interface Presenter extends BasePresenter<View>{
        void nameAuth(String name, String idCard, String front, String back);
    }

}
