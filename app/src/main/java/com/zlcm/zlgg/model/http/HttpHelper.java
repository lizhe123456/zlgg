package com.zlcm.zlgg.model.http;

import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;

import io.reactivex.Flowable;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public interface HttpHelper {

    Flowable<ZLResponse> fetchMobileCodeInfo(String mobile);

    Flowable<ZLResponse<LoginBean>> fetchMobileCodeInfo(String mobile,String code);

    Flowable<ZLResponse<UserInfoBean>> fetchgetUserInfo(String username);
}
