package com.zlcm.zlgg.model;

import com.zlcm.zlgg.model.bean.ChargInfoBean;
import com.zlcm.zlgg.model.bean.ChargingBean;
import com.zlcm.zlgg.model.bean.HomePageBean;
import com.zlcm.zlgg.model.bean.HotBean;
import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.bean.NewVersionInfoBean;
import com.zlcm.zlgg.model.bean.OrderListBean;
import com.zlcm.zlgg.model.bean.PeripheryDetailsBean;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.db.RealmHelper;
import com.zlcm.zlgg.model.http.HttpHelper;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import javax.inject.Inject;
import io.reactivex.Flowable;
import okhttp3.RequestBody;

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
    public Flowable<ZL2Response<UserInfoBean>> fetchgetUserInfo(String username) {
        return mHttpHelper.fetchgetUserInfo(username);
    }

    @Override
    public Flowable<ZLResponse> fetchUploadFile(RequestBody file) {
        return mHttpHelper.fetchUploadFile(file);
    }

    @Override
    public Flowable<ZLResponse> fetchSetNickName(String nickName) {
        return mHttpHelper.fetchSetNickName(nickName);
    }

    @Override
    public Flowable<ZLResponse> fetchProvingPhone(String mobile, String code) {
        return mHttpHelper.fetchProvingPhone(mobile,code);
    }

    @Override
    public Flowable<ZLResponse> fetchExitLogin() {
        return mHttpHelper.fetchExitLogin();
    }

    @Override
    public Flowable<ZLResponse<NewVersionInfoBean>> fetchNewVersion() {
        return mHttpHelper.fetchNewVersion();
    }

    @Override
    public Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeviceBList(double longitude, double latitude,int page) {
        return mHttpHelper.fetchDeviceBList(longitude, latitude, page);
    }

    @Override
    public Flowable<ZLResponse<HomePageBean>> fetchHomePage(double longitude, double latitude,int first) {
        return mHttpHelper.fetchHomePage(longitude, latitude, first);
    }

    @Override
    public Flowable<ZLResponse<ChargingBean>> fetchChargingInfo(int did) {
        return mHttpHelper.fetchChargingInfo(did);
    }

    @Override
    public Flowable<ZLResponse<HotBean>> fetchHotAdvertInfo(int page, int size) {
        return mHttpHelper.fetchHotAdvertInfo(page,size);
    }

    @Override
    public Flowable<ZLResponse<PeripheryDetailsBean>> fetchPeripheryDetailsInfo(int did,int page, int size, int type) {
        return mHttpHelper.fetchPeripheryDetailsInfo(did,page,size,type);
    }

    @Override
    public Flowable<ZL2Response<ChargInfoBean>> fetchgetSubmitAdvertInfo(String list, RequestBody file, String phone,String desc, String address, long duration) {
        return mHttpHelper.fetchgetSubmitAdvertInfo(list, file, phone,desc, address, duration);
    }

    @Override
    public Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeliveryDeviceList(String province, String city, String area, String devices,int page) {
        return mHttpHelper.fetchDeliveryDeviceList(province, city, area, devices,page);
    }

    @Override
    public Flowable<ZL2Response<String>> fetchPayInfo(int order,int type) {
        return mHttpHelper.fetchPayInfo(order,type);
    }

    @Override
    public Flowable<ZLResponse<OrderListBean>> fetchOrderListInfo(int size, int page) {
        return mHttpHelper.fetchOrderListInfo(size, page);
    }

    @Override
    public Flowable<ZLResponse> fetchAuthName(String name, String idCard, String front, String back) {
        return mHttpHelper.fetchAuthName(name, idCard, front, back);
    }

    @Override
    public Flowable<ZLResponse> fetchStoreAuth(String name, String address, String phone, String image) {
        return mHttpHelper.fetchStoreAuth(name, address, phone, image);
    }

    @Override
    public Flowable<ZL2Response<String>> getNavigation() {
        return mHttpHelper.getNavigation();
    }

    @Override
    public Flowable<ZLResponse> addPageView(int aid) {
        return mHttpHelper.addPageView(aid);
    }

    @Override
    public Flowable<ZLResponse> fetchUploadFeed(String desc, String phone) {
        return mHttpHelper.fetchUploadFeed(desc,phone);
    }


}
