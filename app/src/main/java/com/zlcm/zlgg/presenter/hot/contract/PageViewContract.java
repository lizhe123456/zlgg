package com.zlcm.zlgg.presenter.hot.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;

/**
 * Created by lizhe on 2018/1/23.
 * 类介绍：
 */

public interface PageViewContract {

    interface Presenter extends BasePresenter<View>{
        void submitPageView(int aid);
    }

    interface View extends BaseView{
    }
}
