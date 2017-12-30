package com.zlcm.zlgg.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhe on 2017/12/30.
 * 类介绍：
 */

public class PeripheryDetailsBean implements Serializable {

    private List<AdvertBean> advert;
    private int hot;
    private int visitorsFlowrate;
    private double charging;

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public int getVisitorsFlowrate() {
        return visitorsFlowrate;
    }

    public void setVisitorsFlowrate(int visitorsFlowrate) {
        this.visitorsFlowrate = visitorsFlowrate;
    }

    public double getCharging() {
        return charging;
    }

    public void setCharging(double charging) {
        this.charging = charging;
    }

    public List<AdvertBean> getAdvert() {
        return advert;
    }

    public void setAdvert(List<AdvertBean> advert) {
        this.advert = advert;
    }
}
