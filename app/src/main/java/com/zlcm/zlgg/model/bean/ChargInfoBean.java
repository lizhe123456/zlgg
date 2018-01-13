package com.zlcm.zlgg.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhe on 2018/1/4.
 * 类介绍：
 */

public class ChargInfoBean implements Serializable {

    private int order_number;
    private List<Device> list;
    private String startTime;
    private String duration;
    private int orderState;
    private int advertState;
    private float price;
    private String advert;
    private String desc;

    public int getOrder_number() {
        return order_number;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public List<Device> getList() {
        return list;
    }

    public void setList(List<Device> list) {
        this.list = list;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getAdvertState() {
        return advertState;
    }

    public void setAdvertState(int advertState) {
        this.advertState = advertState;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAdvert() {
        return advert;
    }

    public void setAdvert(String advert) {
        this.advert = advert;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public class Device implements Serializable{
        private Integer did;

        private String mac;

        private String address;

        private double dlatitude;

        private double dlongitude;

        private String ip;

        private Byte state;

        private Integer household;

        private String desc;

        private String province;

        private String city;

        private String area;

        private static final long serialVersionUID = 1L;

        public Integer getDid() {
            return did;
        }

        public void setDid(Integer did) {
            this.did = did;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac == null ? null : mac.trim();
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address == null ? null : address.trim();
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
            this.ip = ip == null ? null : ip.trim();
        }

        public Byte getState() {
            return state;
        }

        public void setState(Byte state) {
            this.state = state;
        }

        public Integer getHousehold() {
            return household;
        }

        public void setHousehold(Integer household) {
            this.household = household;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc == null ? null : desc.trim();
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province == null ? null : province.trim();
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city == null ? null : city.trim();
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area == null ? null : area.trim();
        }
    }


}
