package com.zlcm.zlgg.model.http.api;

import com.zlcm.zlgg.model.bean.AdvertDetailsBean;
import com.zlcm.zlgg.model.bean.AuthInfo;
import com.zlcm.zlgg.model.bean.ChargInfoBean;
import com.zlcm.zlgg.model.bean.ChargingBean;
import com.zlcm.zlgg.model.bean.HomePageBean;
import com.zlcm.zlgg.model.bean.HotBean;
import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.bean.NewVersionInfoBean;
import com.zlcm.zlgg.model.bean.OrderListBean;
import com.zlcm.zlgg.model.bean.PeripheryDetailsBean;
import com.zlcm.zlgg.model.bean.PeripheryDeviceBean;
import com.zlcm.zlgg.model.bean.StoreInfo;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.http.response.ZL2Response;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import java.util.List;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public interface ZLApi {

    /**
     * 注册登录
     * @param mobile
     * @param code
     * @return
     */
    @GET("/api/user/login")
    Flowable<ZLResponse<LoginBean>> getLoginBeanInfo(@Query("mobile")String mobile,@Query("code") String code);

    /**
     * 获取手机验证码
     * @param mobile
     * @return
     */
    @GET("/api/user/phoneCode")
    Flowable<ZLResponse> getMobileCodeInfo(@Query("mobile") String mobile);

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    @FormUrlEncoded
    @POST("/api/user/info")
    Flowable<ZL2Response<UserInfoBean>> getUserInfo(@Field("username") String username);

    /**
     * 上传头像
     * @return
     */
    @POST("/api/user/update/avatar")
    @Multipart
    Flowable<ZLResponse> uploadFile(@Part("avatar\"; filename=\"avatar.jpg\"") RequestBody file);

    /**
     * 上传昵称
     * @return
     */
    @FormUrlEncoded
    @POST("/api/user/update/info")
    Flowable<ZLResponse> setNickName(@Field("nickName") String nickName);

    /**
     * 验证手机号
     * @param mobile
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("/api/user/proving/phone")
    Flowable<ZLResponse> provingPhone(@Field("mobile")String mobile, @Field("code") String code);

    /**
     * 退出登录
     * @return
     */
    @POST("/api/user/logout")
    Flowable<ZLResponse> exitLogin();

    /**
     * 获取最新版本
     * @return
     */
    @POST("/api/setting/newversion")
    Flowable<ZLResponse<NewVersionInfoBean>> getNewVersion();

    /**
     * 反馈意见
     * @param desc
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("/api/setting/feedback")
    Flowable<ZLResponse> uploadFeed(@Field("desc") String desc, @Field("phone") String phone);

    /**
     * 获取首页设配信息
     * @param longitude
     * @param latitude
     * @return
     */
    @GET("/api/user/homepage")
    Flowable<ZLResponse<HomePageBean>> getHomePageInfo(@Query("longitude") double longitude,@Query("latitude") double latitude,@Query("first") int first);

    /**
     * 获取热门广告
     * @return
     */
    @FormUrlEncoded
    @POST("/api/advert/hot")
    Flowable<ZLResponse<HotBean>> getHotAdvertInfo(@Field("page") int page, @Field("size") int size);

    /**
     * 获取广告详情
     * @param aid
     * @return
     */
    @FormUrlEncoded
    @POST("/api/advert/details")
    Flowable<ZLResponse<AdvertDetailsBean>> getAdvertDetails(@Field("aid") int aid);

    /**
     * 获取推荐广告
     * @return
     */
    @POST("/api/advert/recommend")
    Flowable<ZLResponse> getRecommendInfo();

    /**
     * 获取附近广告牌
     * @return
     */
    @FormUrlEncoded
    @POST("/api/device/periphery")
    Flowable<ZLResponse<PeripheryDeviceBean>> getDeviceListInfo(@Field("longitude") double longitude,
                                                                @Field("latitude") double latitude,
                                                                @Field("page") int page,
                                                                @Field("size") int size);

    /**
     * 获取设配详情
     * @param did
     * @return
     */
    @FormUrlEncoded
    @POST("/api/device/charging")
    Flowable<ZLResponse<ChargingBean>> getChargingInfo(@Field("did") int did);

    @FormUrlEncoded
    @POST("/api/advert/fordevice")
    Flowable<ZLResponse<PeripheryDetailsBean>> getPeripheryDetailsInfo(@Field("did") int did,@Field("page") int page, @Field("size") int size, @Field("type") int type);

    /**
     * 提交广告
     */
    @Multipart
    @POST("/api/advert/submit")
    Flowable<ZL2Response<ChargInfoBean>> getSubmitAdvert(@Query("devices")String list,
                                                        @Part("advert\"; filename=\"advert.jpg\"") RequestBody file,
                                                        @Query("phone") String phone,
                                                        @Query("desc") String desc,
                                                        @Query("address") String address,
                                                        @Query("duration") long duration);

    /**
     * 通过省市区获取设配 + 已有设配
     */
    @FormUrlEncoded
    @POST("/api/device/delivery")
    Flowable<ZLResponse<PeripheryDeviceBean>> getDeliveryDeviceList(@Field("province") String province,
                                                                    @Field("city") String city,
                                                                    @Field("area") String area,
                                                                    @Field("devices") String devices,
                                                                    @Query("page") int page);

    /**
     * 支付接口
     * @param order
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/pay")
    Flowable<ZL2Response<String>> pay(@Field("order") int order,@Field("type") int type);

    /**
     * 获取订单列表
     * @param size
     * @param page
     * @return
     */
    @FormUrlEncoded
    @POST("/api/order/list")
    Flowable<ZLResponse<OrderListBean>> getOrderInfo(@Field("size") int size,@Field("page") int page);

    /**
     * 实名认证
     * @param name
     * @param idCard
     * @param front
     * @param back
     * @return
     */
    @FormUrlEncoded
    @POST("/api/user/authen")
    Flowable<ZLResponse> uploadAuthInfo(@Field("name") String name, @Field("idCard") String idCard, @Field("front") String front, @Field("back") String back);

    /**
     * 商家认证
     */
    @FormUrlEncoded
    @POST("/api/user/storeAuthen")
    Flowable<ZLResponse> uploadStoreAuthInfo(@Field("name") String name, @Field("address") String address, @Field("phone") String phone, @Field("image") String image);

    @GET("/api/user/navigation")
    Flowable<ZL2Response<String>> navigation();

}


