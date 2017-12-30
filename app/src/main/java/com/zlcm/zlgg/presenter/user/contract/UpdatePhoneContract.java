package com.zlcm.zlgg.presenter.user.contract;

import com.zlcm.zlgg.base.BasePresenter;
import com.zlcm.zlgg.base.BaseView;
import com.zlcm.zlgg.model.http.response.ZLResponse;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：更换手机号
 */

public interface UpdatePhoneContract {

    interface View extends BaseView{
       void codeState(ZLResponse response);

        void provingSate(ZLResponse response);
    }

    interface Presenter extends BasePresenter<View>{

        void getUpdateCode(String phone);

        void provingPhone(String mobile,String code);
    }

}
