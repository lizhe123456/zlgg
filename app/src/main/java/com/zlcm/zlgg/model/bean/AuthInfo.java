package com.zlcm.zlgg.model.bean;

import java.io.Serializable;

/**
 * Created by lizhe on 2018/1/8.
 * 类介绍：
 */

public class AuthInfo implements Serializable {


    /**
     * realname : 张三
     * sex : 男
     * nation : 侗
     * born : 19760613
     * address : 贵州省都匀市甘塘镇长红机器厂散居户169号
     * idcard : 522701197606131938
     * side : front
     * orderid : 339057896
     */

    private String realname;
    private String sex;
    private String nation;
    private String born;
    private String address;
    private String idcard;
    private String side;
    private int orderid;

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
}
