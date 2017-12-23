package com.zlcm.zlgg.utils;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zlcm.zlgg.app.Constants;

/**
 * Created by Administrator on 2017/8/17 0017.
 * 日志打印工具
 */

public class LogUtil {

    /**
     * 初始化log工具，在app入口处调用
     *
     * @param isLogEnable 是否打印log
     */
    public static void init(boolean isLogEnable) {
        Logger.init("LogHttpInfo")
                .hideThreadInfo()
                .logLevel(isLogEnable ? LogLevel.FULL : LogLevel.NONE)
                .methodOffset(2);
    }

    public static void d(String message) {
        if (Constants.IS_DEBUG){
            Logger.d(message);
        }
    }

    public static void d(String className,String message) {
        if (Constants.IS_DEBUG) {
            Logger.d(className + " : " + message);
        }
    }


    public static void i(String message) {
        if (!Constants.IS_DEBUG)
        Logger.i(message);
    }

    public static void w(String message, Throwable e) {
        if (Constants.IS_DEBUG){
            String info = e != null ? e.toString() : "null";
            Logger.w(message + "：" + info);
        }
    }

    public static void e(String message, Throwable e) {
        if (Constants.IS_DEBUG) {
            Logger.e(e, message);
        }
    }

    public static void json(String json) {
        if (Constants.IS_DEBUG) {
            Logger.json(json);
        }
    }
}
