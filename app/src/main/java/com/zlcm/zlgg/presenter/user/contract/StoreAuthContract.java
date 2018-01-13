package com.zlcm.zlgg.presenter.user.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;

/**
 * Created by lizhe on 2018/1/11.
 * 类介绍：
 */

public interface StoreAuthContract {

    interface View extends BaseView{
        void showContent();
    }

    interface Presenter extends BasePresenter<View>{
        void storeAuth(String name,String address,String phone,String image);
    }
}
