package com.zlcm.zlgg.app;

import com.zlcm.zlgg.lib.PositionEntity;
import com.zlcm.zlgg.utils.FileUtil;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class Constants {
    //正式关闭日志
    public static final boolean IS_DEBUG = true;
    public static final String PATH_CACHE = FileUtil.getFileDir("Http") + "/caches";
//    public static final String ZL_URL = "http://39.106.34.25";
//    public static final String ZL_URL = "http://libolg.vicp.io";

    public static final String ZL_URL = "http://192.168.1.201:8080";
    public static final String JH_URL = "http://apis.juhe.cn";

    public static final String ZL_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJxm1FL3RwDgRBWkBIn6JzqNQ8Mn/8nEumHAOYmVMhsrjG+lckmmYr4SVvO/4fzeTUd6JO0kGVoFUt3loQJq8j8CAwEAAQ==";

    public static final String QY_APP_KEY = "ec85a9277c3cb005382437e70f792640";
    public static final String QY_SECRET_ = "20D14E1D32CEFADD672970F37C4C7410";

    public final static int NAMEAUTH = 1;
    public final static int STOREUTH = 2;

    //我的位置信息
    public static PositionEntity loction;
    public static String city;
    public static String province;
    public static String district;
    //微信
    public static final String APP_IP_WX = "";
    //商户号
    public static final String MCH_ID_WX = "";
    //  API密钥，在商户平台设置
    public static final  String API_KEY_WX="";

    //支付宝
    public static final String APP_IP_ALI = "";
    // 商户PID
    public static final String PARTNER = "";
    // 商户收款账号
    public static final String SELLER = "";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    public static final String NOTIFY_URL = "http://www.yourdomain.com/order/alipay_notify_app";
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;

}
