package com.zlcm.zlgg.presenter.user.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.UserInfoBean;

/**
 * Created by lizhe on 2017/12/22.
 * 类介绍：
 */

public interface UserInfoContract {

    interface View extends BaseView{
        void showContent(UserInfoBean bean);
    }

    interface Presenter extends BasePresenter<View>{

        void updateAvatar();

        void authen();

        void getUserInfo(String username);
    }
}
