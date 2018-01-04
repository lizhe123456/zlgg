package com.zlcm.zlgg.model.http;

import com.zlcm.zlgg.model.bean.ChargInfoBean;
import com.zlcm.zlgg.model.bean.ChargingBean;
import com.zlcm.zlgg.model.bean.HomePageBean;
import com.zlcm.zlgg.model.bean.HotBean;
import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.bean.NewVersionInfoBean;
import com.zlcm.zlgg.model.bean.PeripheryDetailsBean;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.http.api.ZLApi;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.model.http.response.ZLResponse;

import java.util.List;

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
    public Flowable<ZLResponse<NewVersionInfoBean>> fetchNewVersion(String version) {
        return zlApi.getNewVersion(version);
    }

    @Override
    public Flowable<ZLResponse> fetchUploadFeed(String desc, String phone) {
        return zlApi.uploadFeed(desc,phone);
    }

    @Override
    public Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeviceBList(double longitude, double latitude,int page, int size ) {
        return zlApi.getDeviceListInfo(longitude, latitude, page, size);
    }

    @Override
    public Flowable<ZLResponse<HomePageBean>> fetchHomePage(double longitude, double latitude) {
        return zlApi.getHomePageInfo(longitude, latitude);
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
    public Flowable<ZLResponse<ChargInfoBean>> fetchgetSubmitAdvertInfo(List<Integer> list, RequestBody file, String desc, String address, long duration) {
        return zlApi.getSubmitAdvert(list,file,desc,address,duration);
    }

    @Override
    public Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeliveryDeviceList(String province, String city, String area, List<Integer> devices,int page) {
        return zlApi.getDeliveryDeviceList(province, city, area, devices,page);
    }


}
