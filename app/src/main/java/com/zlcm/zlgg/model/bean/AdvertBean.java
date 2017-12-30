package com.zlcm.zlgg.model.bean;

import com.zlcm.zlgg.utils.TimeUtils;

import java.io.Serializable;

/**
 * Created by lizhe on 2017/12/25.
 * 类介绍：广告实体
 */

public class AdvertBean implements Serializable{
    private int aid;
    //广告海报
    private String advertImg;
    //广告文字信息
    private String textInfo;
    //开始时间
    private long startTime;
    //点击量
    private int hits;
    //联系方式
    private String phone;
    //发布人
    private String nickname;
    //头像
    private String avatar;
    //信用分
    private int credit;
    //真实姓名
    private String realName;
    //商家
    private int sid;
    //所在地或门店地址
    private String address;


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getAdvertImg() {
        return advertImg;
    }

    public void setAdvertImg(String advertImg) {
        this.advertImg = advertImg;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public String getStartTime() {
        return TimeUtils.parseDate(startTime);
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
