package com.zlcm.zlgg.model.http.api;

import com.zlcm.zlgg.model.bean.LoginBean;
import com.zlcm.zlgg.model.bean.UserInfoBean;
import com.zlcm.zlgg.model.http.response.ZLResponse;
import io.reactivex.Flowable;
import retrofit2.http.GET;
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
   @GET("/api/user/info")
    Flowable<ZLResponse<UserInfoBean>> getUserInfo(@Query("username") String username);


}
