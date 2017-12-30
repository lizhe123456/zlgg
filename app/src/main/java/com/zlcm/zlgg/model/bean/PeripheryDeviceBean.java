package com.zlcm.zlgg.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhe on 2017/12/29.
 * 类介绍：周边设配
 */

public class PeripheryDeviceBean implements Serializable{

    List<Device> devices;

    public List<Device> getList() {
        return devices;
    }

    public void setList(List<Device> devices) {
        this.devices = devices;
    }

    public class Device implements Serializable{

        int did;
        String address;
        List<String> advert;

        public String getAddress() {
           return address;
       }

        public void setAddress(String address) {
           this.address = address;
       }

        public List<String> getAdvert() {
            return advert;
        }

        public void setAdvert(List<String> advert) {
            this.advert = advert;
        }

        public int getDid() {
            return did;
        }

        public void setDid(int did) {
            this.did = did;
        }
    }

}
