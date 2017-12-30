package com.zlcm.zlgg.model.bean;

import java.io.Serializable;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：设配实体
 */

public class DeviceBean implements Serializable{
    private Integer did;

    private String address;

    private double dlatitude;

    private double dlongitude;

    private String ip;

    private Integer household;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDlatitude() {
        return dlatitude;
    }

    public void setDlatitude(double dlatitude) {
        this.dlatitude = dlatitude;
    }

    public double getDlongitude() {
        return dlongitude;
    }

    public void setDlongitude(double dlongitude) {
        this.dlongitude = dlongitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getHousehold() {
        return household;
    }

    public void setHousehold(Integer household) {
        this.household = household;
    }
}
