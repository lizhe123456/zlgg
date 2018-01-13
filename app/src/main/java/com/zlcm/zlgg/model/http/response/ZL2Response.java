package com.zlcm.zlgg.model.http.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class ZL2Response<T> implements Serializable{

    private String message;
    private int code;
    private T info;

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
