package com.zlcm.zlgg.model.http;

import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.http.api.ZLApi;
import com.zlcm.zlgg.model.http.response.ZLResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class HttpHelperImpl implements HttpHelper{

    public ZLApi zlApi;

    @Inject
    public HttpHelperImpl(ZLApi zlApi){
        this.zlApi = zlApi;
    }


    @Override
    public Flowable<ZLResponse> fetchMobileCodeInfo(String mobile) {
        return zlApi.getMobileCodeInfo(mobile);
    }

    @Override
    public Flowable<ZLResponse<LoginBean>> fetchMobileCodeInfo(String mobile, String code) {
        return zlApi.getLoginBeanInfo(mobile,code);
    }

    @Override
    public Flowable<ZLResponse<UserInfoBean>> fetchgetUserInfo(String username) {
        return zlApi.getUserInfo(username);
    }
}
