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
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import io.reactivex.Flowable;
import okhttp3.RequestBody;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public interface HttpHelper {

    Flowable<ZLResponse> fetchMobileCodeInfo(String mobile);

    Flowable<ZLResponse<LoginBean>> fetchMobileCodeInfo(String mobile,String code);

    Flowable<ZL2Response<UserInfoBean>> fetchgetUserInfo(String username);

    Flowable<ZLResponse> fetchUploadFile(RequestBody file);

    Flowable<ZLResponse> fetchSetNickName(String nickName);

    Flowable<ZLResponse> fetchProvingPhone(String mobile,String code);

    Flowable<ZLResponse> fetchExitLogin();

    Flowable<ZLResponse<NewVersionInfoBean>> fetchNewVersion();

    Flowable<ZLResponse> fetchUploadFeed(String desc, String phone);

    Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeviceBList(double longitude, double latitude,int page);

    Flowable<ZLResponse<HomePageBean>> fetchHomePage(double longitude, double latitude, int first);

    Flowable<ZLResponse<ChargingBean>> fetchChargingInfo(int did);

    Flowable<ZLResponse<HotBean>> fetchHotAdvertInfo(int page, int size);

    Flowable<ZLResponse<PeripheryDetailsBean>> fetchPeripheryDetailsInfo(int did,int page, int size, int type);

    Flowable<ZL2Response<ChargInfoBean>> fetchgetSubmitAdvertInfo(String list,
                                                                 RequestBody file,
                                                                 String phone,
                                                                 String desc,
                                                                 String address,
                                                                 long duration);

    Flowable<ZLResponse<PeripheryDeviceBean>> fetchDeliveryDeviceList(String province, String city, String area, String devices,int page);

    Flowable<ZL2Response<String>> fetchPayInfo(int order,int type);

    Flowable<ZLResponse<OrderListBean>> fetchOrderListInfo(int size, int page);

    Flowable<ZLResponse> fetchAuthName(String name, String idCard, String front, String back);

    Flowable<ZLResponse> fetchStoreAuth(String name, String address, String phone, String image);

    Flowable<ZL2Response<String>> getNavigation();

    Flowable<ZLResponse> addPageView(int aid);

}
