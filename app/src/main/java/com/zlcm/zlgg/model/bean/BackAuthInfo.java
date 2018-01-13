package com.zlcm.zlgg.model.bean;

import java.io.Serializable;

/**
 * Created by lizhe on 2018/1/8.
 * 类介绍：
 */

public class BackAuthInfo implements Serializable{

    /**
     * begin : 20130501
     * department : 东台市公安局
     * end : 20180501
     * side : back
     * orderid : 478799279
     */

    private String begin;
    private String department;
    private String end;
    private String side;
    private int orderid;

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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
