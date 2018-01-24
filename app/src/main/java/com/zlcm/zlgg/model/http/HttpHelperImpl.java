package com.zlcm.zlgg.model.http;

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
import com.zlcm.zlgg.model.http.api.ZLApi;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import javax.inject.Inject;
import io.reactivex.Flowable;
import okhttp3.RequestBody;

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
    public Flowable<ZL2Response<UserInfoBean>> fetchgetUserInfo(String username) {
        return zlApi.getUserInfo(username);
    }

    @Override
    public Flowable<ZLResponse> fetchUploadFile(RequestBody file) {
        return zlApi.uploadFile(file);
    }

    @Override
    public Flowable<ZLResponse> fetchSetNickName(String nickName) {
        return zlApi.setNickName(nickName);
    }


    @Override
    public Flowable<ZLResponse> fetchProvingPhone(String mobile, String code) {
        return zlApi.provingPhone(mobile, code);
    }

    @Override
    public Flowable<ZLResponse> fetchExitLogin() {
        return zlApi.exitLogin();
    }

    @Override
    public Flowable<ZLResponse<NewVersionInfoBean>> fetchNewVersion() {
        return zlApi.getNewVersion();
    }

    @Override
    public Flowable<ZLResponse> fetchUploadFeed(String desc, String phone) {
        return zlApi.uploadFeed(desc,phone);
    }

    @Override
    public Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeviceBList(double longitude, double latitude,int page) {
        return zlApi.getDeviceListInfo(longitude, latitude, page);
    }

    @Override
    public Flowable<ZLResponse<HomePageBean>> fetchHomePage(double longitude, double latitude,int first) {
        return zlApi.getHomePageInfo(longitude, latitude,first);
    }

    @Override
    public Flowable<ZLResponse<ChargingBean>> fetchChargingInfo(int did) {
        return zlApi.getChargingInfo(did);
    }

    @Override
    public Flowable<ZLResponse<HotBean>> fetchHotAdvertInfo(int page, int size) {
        return zlApi.getHotAdvertInfo(page,size);
    }

    @Override
    public Flowable<ZLResponse<PeripheryDetailsBean>> fetchPeripheryDetailsInfo(int did,int page, int size, int type) {
        return zlApi.getPeripheryDetailsInfo(did,page,size,type);
    }

    @Override
    public Flowable<ZL2Response<ChargInfoBean>> fetchgetSubmitAdvertInfo(String list, RequestBody file, String phone,String desc, String address, long duration) {
        return zlApi.getSubmitAdvert(list,file,phone,desc,address,duration);
    }

    @Override
    public Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeliveryDeviceList(String province, String city, String area, String devices,int page) {
        return zlApi.getDeliveryDeviceList(province, city, area, devices,page);
    }

    @Override
    public Flowable<ZL2Response<String>> fetchPayInfo(int order,int type) {
        return zlApi.pay(order,type);
    }

    @Override
    public Flowable<ZLResponse<OrderListBean>> fetchOrderListInfo(int size, int page) {
        return zlApi.getOrderInfo(size, page);
    }

    @Override
    public Flowable<ZLResponse> fetchAuthName(String name, String idCard, String front, String back) {
        return zlApi.uploadAuthInfo(name,idCard,front,back);
    }

    @Override
    public Flowable<ZLResponse> fetchStoreAuth(String name, String address, String phone, String image) {
        return zlApi.uploadStoreAuthInfo(name, address, phone, image);
    }

    @Override
    public Flowable<ZL2Response<String>> getNavigation() {
        return zlApi.navigation();
    }

    @Override
    public Flowable<ZLResponse> addPageView(int aid) {
        return zlApi.pageview(aid);
    }


}
