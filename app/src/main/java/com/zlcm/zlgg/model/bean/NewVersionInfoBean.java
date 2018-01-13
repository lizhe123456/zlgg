package com.zlcm.zlgg.model.bean;

import com.google.gson.Gson;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：
 */

public class NewVersionInfoBean {

    private String version;

    public AppVersion getVersion() {
        return new Gson().fromJson(version,AppVersion.class);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public class AppVersion{
        String code;
        String update;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }
    }
}
