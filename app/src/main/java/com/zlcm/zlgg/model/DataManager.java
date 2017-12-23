package com.zlcm.zlgg.model;

import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.db.RealmHelper;
import com.zlcm.zlgg.model.http.HttpHelper;
import com.zlcm.zlgg.model.http.response.ZLResponse;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 * 数据中心(网络、数据库、本地存储)
 */

public class DataManager implements HttpHelper,RealmHelper {
    HttpHelper mHttpHelper;
    RealmHelper mDbHelper;

    @Inject
    public DataManager(HttpHelper mHttpHelper, RealmHelper mDbHelper) {
        this.mHttpHelper = mHttpHelper;
        this.mDbHelper = mDbHelper;
    }

    @Override
    public Flowable<ZLResponse> fetchMobileCodeInfo(String mobile) {
        return mHttpHelper.fetchMobileCodeInfo(mobile);
    }

    @Override
    public Flowable<ZLResponse<LoginBean>> fetchMobileCodeInfo(String mobile, String code) {
        return mHttpHelper.fetchMobileCodeInfo(mobile,code);
    }

    @Override
    public Flowable<ZLResponse<UserInfoBean>> fetchgetUserInfo(String username) {
        return mHttpHelper.fetchgetUserInfo(username);
    }

}
