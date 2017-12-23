package com.zlcm.zlgg.model.bean;

import java.io.Serializable;

/**
 * Created by lizhe on 2017/12/11 0011.
 * 目标定在月亮之上，即使失败，也可以落在众星之间。
 */

public class LoginBean implements Serializable{

    /**
     * loginId : FtoURcuyR0oxtOt0D3o6ztr9Qd8sRdgYxeJxeUE6UmspuCmUCDrhjz4mcmc+ejYmxbvloumJ52UeLcie4Jc/GyvRYwWA4qboskuTOv2smEThq56ofo4wI+fPMWYAHFuAN3EzobSRQof9rO+eCTywO8IzIU43n1S4WHmPWia4mgY=
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MTIyMjM2ODYxMDksInBheWxvYWQiOiJ7XCJ1aWRcIjoxLFwidXNlcm5hbWVcIjpcIjE3Njg4OTQzOTcyXCIsXCJjb2RlXCI6bnVsbCxcInBhc3N3b3JkXCI6bnVsbCxcInNhbHRcIjpudWxsLFwic3RhdGVcIjoxLFwibG9ja2VkXCI6MCxcInJlZ2lzdGVySXBcIjpudWxsLFwicmVnaXN0ZXJUaW1lXCI6MTUxMzkwODExMjAwMCxcImxhc3RMb2dpbklwXCI6XCIxOTIuMTY4LjEuMTA5XCIsXCJsYXN0TG9naW5UaW1lXCI6MTUxMzkwODExMjAwMH0ifQ.4fg3PpgEIFyABGGcXepFaKMnW89JVwZ1MO21Pj-ZDuY
     */

    private String loginId;
    private String token;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
