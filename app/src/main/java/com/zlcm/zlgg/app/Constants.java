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
    public static final String ZL_URL = "http://192.168.1.201:8080";

    public static final String ZL_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJxm1FL3RwDgRBWkBIn6JzqNQ8Mn/8nEumHAOYmVMhsrjG+lckmmYr4SVvO/4fzeTUd6JO0kGVoFUt3loQJq8j8CAwEAAQ==";

    public final static int NAMEAUTH = 1;
    public final static int STOREUTH = 2;

    //我的位置信息
    public static PositionEntity loction;
    public static String city;
    public static String province;
    public static String district;
}
