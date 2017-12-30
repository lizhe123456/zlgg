package com.zlcm.zlgg.model.bean;

import java.io.Serializable;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：设配详情实体计费信息
 */

public class ChargingBean implements Serializable{

    private String address;
    private int charging;
    private int household;
    private int visitorsflowrate;
    private int authCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharging() {
        return charging + "/小时";
    }

    public String getHousehold() {
        return household + " 人";
    }


    public String getVisitorsFlowrate() {
        return visitorsflowrate + "/日";
    }

    public void setCharging(int charging) {
        this.charging = charging;
    }

    public void setHousehold(int household) {
        this.household = household;
    }

    public void setVisitorsflowrate(int visitorsflowrate) {
        this.visitorsflowrate = visitorsflowrate;
    }

    public int getAuthCode() {
        return authCode;
    }

    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }
}
