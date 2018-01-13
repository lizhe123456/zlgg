package com.zlcm.zlgg.presenter.setting.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;

/**
 * Created by lizhe on 2018/1/13.
 * 类介绍：
 */

public interface FeedBackContract {

    interface View extends BaseView {

        void back();

    }

    interface Presenter extends BasePresenter<View>{

        void feedback(String desc,String phone);

    }

}
