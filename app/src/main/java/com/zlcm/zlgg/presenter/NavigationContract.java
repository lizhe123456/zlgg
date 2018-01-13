package com.zlcm.zlgg.presenter;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;

/**
 * Created by lizhe on 2018/1/12.
 * 类介绍：
 */

public interface NavigationContract {

    interface View extends BaseView{
        void showContent(String img);
    }

    interface Presenter extends BasePresenter<View>{
        void getNavigation();
    }
}
