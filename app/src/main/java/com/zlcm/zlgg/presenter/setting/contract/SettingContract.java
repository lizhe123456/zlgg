package com.zlcm.zlgg.presenter.setting.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.NewVersionInfoBean;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：
 */

public interface SettingContract {

    interface View extends BaseView{

        void showNewVersion(NewVersionInfoBean bean);

        void exit();

        void back();
    }

    interface Presenter extends BasePresenter<View>{

        void exitLogin();

        void getNewVersion(String version);

        void feedback(String desc,String phone);
    }
}
