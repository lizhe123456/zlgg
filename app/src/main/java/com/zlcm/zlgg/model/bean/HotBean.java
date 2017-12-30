package com.zlcm.zlgg.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lizhe on 2017/12/29.
 * 类介绍：
 */

public class HotBean implements Serializable{
    private List<AdvertBean> hot;

    public List<AdvertBean> getHot() {
        return hot;
    }

    public void setHot(List<AdvertBean> hot) {
        this.hot = hot;
    }
}
