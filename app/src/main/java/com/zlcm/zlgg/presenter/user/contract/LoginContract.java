package com.zlcm.zlgg.presenter.user.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public interface LoginContract {

    interface View extends BaseView{
        void getResponse(LoginBean bean);
        void codeState(ZLResponse response);
    }

    interface Presenter extends BasePresenter<View>{

        void login(String mobile, String code );

        void getCode(String mobile);
    }

}
