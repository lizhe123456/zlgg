package com.zlcm.zlgg.model.http.exception;

/**
 * Created by Administrator on 2017/12/22.
 * 服务器异常处理类
 */

public class SysException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int CODE_PASSWORD = 601;
    public static final int CODE_NUM = 603;
    public static final int SERVER_ERROR = 404;
    public static final int USER_LOCKED = 504;
    public static final int SQL_ERROR = 406;

    public SysException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public SysException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message ;
        switch (code) {
            case CODE_PASSWORD:
                message = "验证码有误";
                break;
            case SERVER_ERROR:
                message = "服务器错误";
                break;
            case USER_LOCKED:
                message = "由于不正规使用，用户已停用，请联系客服";
                break;
            case SQL_ERROR:
                message = "服务器异常，正在抢修中..";
                break;
            case CODE_NUM:
                message = "验证码获取次数过多，请稍后重试";
                break;
            default:
                message = "服务器异常，工作人员正在抢修中...";
        }
        return message;
    }
}
