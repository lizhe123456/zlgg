package com.zlcm.zlgg.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：首页数据
 */

public class HomePageBean implements Serializable {

    private List<HeadBean> hean;
    private PushInfo pushInfo;
    private List<DeviceBean> deviceList;
    private String logo;
    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public List<HeadBean> getHean() {
        return hean;
    }

    public void setHean(List<HeadBean> hean) {
        this.hean = hean;
    }

    public PushInfo getPushInfo() {
        return pushInfo;
    }

    public void setPushInfo(PushInfo pushInfo) {
        this.pushInfo = pushInfo;
    }

    public List<DeviceBean> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceBean> deviceList) {
        this.deviceList = deviceList;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public class PushInfo implements Serializable{
        int id;
        String title;
        String img;
        String desc;
        String time;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public class HeadBean implements Serializable{
        int id;
        String img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
